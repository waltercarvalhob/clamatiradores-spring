/**
 * Busca ao vivo (sem recarregar a pagina) para as telas de listagem.
 *
 * Convencao esperada em cada template `<modulo>/list.html`:
 *   <form class="live-search-form" method="get" th:action="@{/modulo}">...</form>
 *   <div id="resultados" th:fragment="resultados">...tabela + paginacao...</div>
 *
 * O controller do modulo devolve so o fragmento "resultados" quando a
 * requisicao chega com o header X-Requested-With: XMLHttpRequest (ver
 * SocioController#list); nas demais requisicoes (sem JS, links diretos,
 * F5) devolve a pagina inteira normalmente - a tela funciona dos dois jeitos.
 */
(function () {
	"use strict";

	var DEBOUNCE_MS = 350;
	var currentController = null;

	function debounce(fn, wait) {
		var timer = null;
		return function () {
			var args = arguments;
			clearTimeout(timer);
			timer = setTimeout(function () {
				fn.apply(null, args);
			}, wait);
		};
	}

	function targetFor(form) {
		var selector = form.getAttribute("data-target") || "#resultados";
		return document.querySelector(selector);
	}

	function setLoading(container, loading) {
		if (!container) {
			return;
		}
		container.classList.toggle("live-search-loading", !!loading);
	}

	function fetchAndSwap(url, container) {
		if (currentController) {
			currentController.abort();
		}
		currentController = new AbortController();
		setLoading(container, true);

		fetch(url, {
			headers: { "X-Requested-With": "XMLHttpRequest" },
			signal: currentController.signal
		})
			.then(function (response) {
				if (!response.ok) {
					throw new Error("HTTP " + response.status);
				}
				return response.text();
			})
			.then(function (html) {
				var temp = document.createElement("div");
				temp.innerHTML = html.trim();
				var novo = temp.firstElementChild;
				if (novo && container && container.parentNode) {
					container.parentNode.replaceChild(novo, container);
				}
				window.history.pushState({ liveSearch: true }, "", url);
			})
			.catch(function (err) {
				if (err.name !== "AbortError") {
					// Falha de rede/servidor: deixa o usuario tentar de novo via
					// navegacao normal (submit do formulario continua funcionando).
					console.error("Busca ao vivo falhou, recarregando a pagina normalmente.", err);
					window.location.href = url;
				}
			})
			.finally(function () {
				setLoading(document.querySelector("#resultados"), false);
			});
	}

	function urlFromForm(form) {
		var params = new URLSearchParams(new FormData(form));
		// Uma nova busca sempre volta pra primeira pagina.
		params.delete("page");
		var query = params.toString();
		return form.getAttribute("action") + (query ? "?" + query : "");
	}

	var triggerSearch = debounce(function (form) {
		fetchAndSwap(urlFromForm(form), targetFor(form));
	}, DEBOUNCE_MS);

	document.addEventListener("input", function (e) {
		var form = e.target.closest(".live-search-form");
		if (form) {
			triggerSearch(form);
		}
	});

	document.addEventListener("submit", function (e) {
		var form = e.target.closest(".live-search-form");
		if (form) {
			e.preventDefault();
			fetchAndSwap(urlFromForm(form), targetFor(form));
		}
	});

	// Paginacao e o link "Limpar" ficam dentro do fragmento re-renderizado -
	// delegacao de evento no document, em vez de re-anexar listener a cada swap.
	document.addEventListener("click", function (e) {
		var link = e.target.closest("#resultados a.page-link, a.live-search-clear");
		if (!link) {
			return;
		}
		e.preventDefault();
		var container = document.querySelector("#resultados");
		fetchAndSwap(link.getAttribute("href"), container);

		var form = document.querySelector(".live-search-form");
		if (form && link.classList.contains("live-search-clear")) {
			form.reset();
		}
	});

	window.addEventListener("popstate", function () {
		var form = document.querySelector(".live-search-form");
		if (form) {
			fetchAndSwap(window.location.href, targetFor(form));
		}
	});
})();
