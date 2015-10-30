package com.another.web;

import com.another.service.dropbox.DropBoxService;
import com.another.service.dropbox.DropBoxServiceException;
import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    final DropBoxService dropBoxService;

    @Autowired()
    SimpleController(DropBoxService dropBoxService) {
        this.dropBoxService = dropBoxService;
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "A.N.Other";
    }

    @RequestMapping(value = "/dropBox/auth", method = RequestMethod.POST)
    @ResponseBody
    String dropBoxAuth() {
        return dropBoxService.authStart();
    }

    @RequestMapping(value = "/dropBox/auth/finish", method = RequestMethod.POST)
    @ResponseBody
    String dropBoxAuthFinish(@RequestParam String code) throws DropBoxServiceException {
        return dropBoxService.authFinish(code);
    }

    @RequestMapping(value = "/dropBox/info")
    @ResponseBody
    DbxAccountInfo dropBoxInfo(@RequestParam String accessToken) throws DropBoxServiceException {
        return dropBoxService.accountInfo(accessToken);
    }

    @RequestMapping(value = "/dropBox/list")
    @ResponseBody
    DbxEntry.WithChildren dropBoxFolderList(@RequestParam String accessToken, @RequestParam String path) throws DropBoxServiceException {
        return dropBoxService.list(accessToken, path);
    }

}
