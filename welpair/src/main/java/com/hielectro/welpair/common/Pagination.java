package com.hielectro.welpair.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Pagination {
    private static int currentPageNo;
    private static int startPageNo = 1;
    private static int endPageNo = 5;
    private static int maxPageNo;
    private static int listLength;
    private static int limit = 10;
    private static String url = "";

    public static void init(String requestURL) {
        currentPageNo = 1;
        startPageNo = 1;
        endPageNo = 5;
        maxPageNo = 1;
        listLength = 0;
        url = requestURL;
    }
    
    public static Map<String, Integer> getParameter(int pageNo) {
        Map<String, Integer> response = new HashMap<>();
        currentPageNo = pageNo;

        return getResultMap(response);
    }
    
    public static Map<String, Integer> paging(int length, int pageNo) {
        if (length == 0) length++;
        listLength = length;
        Map<String, Integer> response = new HashMap<>();
        currentPageNo = pageNo;
        maxPageNo = (int) Math.ceil((double) listLength / limit);

        return getResultMap(response);
    }

    public static String getURL() {
        return url;
    }
    private static Map<String, Integer> getResultMap(Map<String, Integer> response) {
        if (endPageNo < currentPageNo) {
            startPageNo = (currentPageNo / endPageNo) * endPageNo + 1;
            endPageNo *= 2;
        } else if (startPageNo > currentPageNo) {
            endPageNo /= 2;
            startPageNo = (currentPageNo / startPageNo) * endPageNo + 1;
        }

        response.put("maxPageNo", maxPageNo);
        response.put("startPageNo", startPageNo);
        response.put("endPageNo", endPageNo);
        response.put("currentPageNo", currentPageNo);

        return response;
    }

}
