package com.clamatiradores.common.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Calcula uma janela de numeros de pagina (com marcadores de reticencia -1)
 * para exibir em listagens com muitas paginas, evitando renderizar um link
 * por pagina (ex.: 900+ links quando ha milhares de registros).
 */
public final class Pagination {

	private static final int SPAN = 2;

	private Pagination() {
	}

	public static List<Integer> window(int currentPage, int totalPages) {
		List<Integer> pages = new ArrayList<>();
		if (totalPages <= 0) {
			return pages;
		}

		int start = Math.max(0, currentPage - SPAN);
		int end = Math.min(totalPages - 1, currentPage + SPAN);

		if (start > 0) {
			pages.add(0);
			if (start > 1) {
				pages.add(-1);
			}
		}

		for (int i = start; i <= end; i++) {
			pages.add(i);
		}

		if (end < totalPages - 1) {
			if (end < totalPages - 2) {
				pages.add(-1);
			}
			pages.add(totalPages - 1);
		}

		return pages;
	}
}
