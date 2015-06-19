/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global $changeEmail, $changePassword, $changeUsername */

$(function () {
    var ChangeSettingsModule = (function () {
        var ChangeEmail = {
            form:         $('.change-email').find('form')[0]    || null,
            $submitBtn:   $('.change-email').find('button')     || null,
            $container:   $('.change-email')                    || null,
            $requestLink: $('#change-email-link')               || null
        };
        var ChangePassword = {
            form:         $('.change-password').find('form')[0] || null,
            $submitBtn:   $('.change-password').find('button')  || null,
            $container:   $('.change-password')                 || null,
            $requestLink: $('#change-password-link')            || null
        };
        var ChangeUsername = {
            form:         $('.change-username').find('form')[0] || null,
            $submitBtn:   $('.change-username').find('button')  || null,
            $container:   $('.change-username')                 || null,
            $requestLink: $('#change-username-link')            || null
        };
        var Util = {
            currentForm: null,
            show: function (obj) {
                obj.$container.fadeIn('slow');
            },
            hide: function (obj) {               
                obj.form.reset();
                obj.$container.fadeOut('slow');
            },
            resetForm: function(obj){
                 obj.form.reset();
            },
            transition: function (objToHide, objToShow){
                this.resetForm(objToHide);
                objToHide.$container.fadeOut('slow', function() {
                    objToShow.$container.fadeIn('slow');
                  });
            },
            showRequestedForm: function (newRequest) {
                if (this.currentRequest == null) {
                    this.show(newRequest);
                    this.currentRequest = newRequest;
                } else if (this.currentRequest != newRequest) {
                    $('.ui-messages').fadeOut('slow');
                    this.transition(this.currentRequest,newRequest);
                    this.currentRequest = newRequest;
                }
            }
        };

        // bind link click handelers
        $.each([ChangeEmail, ChangePassword, ChangeUsername  ], function (index, requestObj) {
                requestObj.$requestLink.click(function () {
                    Util.showRequestedForm(requestObj);
                });
                requestObj.$submitBtn.click(function () {
                    Util.resetForm(requestObj);   
                });
            });
    })();
});
