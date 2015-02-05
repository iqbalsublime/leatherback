package com.rc.leatherback.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.rc.leatherback.exception.ReportNotFoundException;

public class ReportArchiveService {
	private static final Map<String, File> REPORTS_CABINET = new HashMap<String, File>();

	public static String keepReport(File report) {
		String drawerKey = RandomStringUtils.randomAlphanumeric(8);
		REPORTS_CABINET.put(drawerKey, report);

		return drawerKey;
	}

	public static File getReport(String drawerKey) {
		if (!REPORTS_CABINET.containsKey(drawerKey)) {
			throw new ReportNotFoundException(String.format("The report is not found in the reports cabinet by drawer key: %s",
					drawerKey));
		}

		return REPORTS_CABINET.get(drawerKey);
	}

	public static void destroyReport(String drawerKey) {
		if (!REPORTS_CABINET.containsKey(drawerKey)) {
			throw new ReportNotFoundException(String.format("The report is not found in the reports cabinet by drawer key: %s",
					drawerKey));
		}

		REPORTS_CABINET.remove(drawerKey);
	}
}
