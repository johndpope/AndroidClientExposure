package net.ericsson.emovs.exposure.interfaces;

/*
 * Copyright (c) 2017 Ericsson. All Rights Reserved
 *
 * This SOURCE CODE FILE, which has been provided by Ericsson as part
 * of an Ericsson software product for use ONLY by licensed users of the
 * product, includes CONFIDENTIAL and PROPRIETARY information of Ericsson.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF
 * THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 */


import net.ericsson.emovs.utilities.errors.Error;

public interface IAuthenticationListener {
    void onAuthSuccess(String sessionToken);
    void onAuthError(Error error);
}

