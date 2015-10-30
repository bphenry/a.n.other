package com.another.service.dropbox;

import com.dropbox.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class DropBoxService {

    @Value("${dropbox.appKey}")
    private String appKey;
    @Value("${dropbox.appSecret}")
    private String appSecret;

    private DbxAppInfo appInfo;
    private DbxRequestConfig config;

    @PostConstruct
    public void init() {
        appInfo = new DbxAppInfo(appKey, appSecret);
        config = new DbxRequestConfig("A.N.Other", Locale.getDefault().toString());
    }

    public String authStart() {

        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        return webAuth.start();
    }

    public String authFinish(String code) throws DropBoxServiceException {

        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finish(code);
        } catch (DbxException e) {
            throw new DropBoxServiceException(e);
        }
        return authFinish.accessToken;
    }

    public DbxAccountInfo accountInfo(String accessToken) throws DropBoxServiceException {
        DbxClient dbxClient;
        try {
            dbxClient = initAuthorizedClient(accessToken);
            return dbxClient.getAccountInfo();
        } catch (DbxException e) {
            throw new DropBoxServiceException(e);
        }
    }

    public DbxEntry.WithChildren list(String accessToken, String path) throws DropBoxServiceException {

        DbxClient dbxClient;
        if(path == null)
            path= "/";
        try {
            dbxClient = initAuthorizedClient(accessToken);
            return dbxClient.getMetadataWithChildren(path);
        } catch (DbxException e) {
            throw new DropBoxServiceException(e);
        }
    }

    private DbxClient initAuthorizedClient(String accessToken) throws DbxException {
        DbxClient dbxClient = new DbxClient(config, accessToken);
        System.out.println("Linked with account: " + dbxClient.getAccountInfo().displayName);
        return dbxClient;

    }
}
