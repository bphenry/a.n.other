package com.another.service;


public interface SimpleService {

    String authStart();
    String authFinish(String code) throws SimpleServiceException;
    String accountInfo(String accessToken) throws SimpleServiceException;
    String listFolders(String accessToken) throws SimpleServiceException;
}
