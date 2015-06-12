/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global $changeEmail, $changePassword, $changeUsername */

$.fn.clearForm = function () {
    return this.each(function () {
        var type = this.type, tag = this.tagName.toLowerCase();
        if (tag === 'form')
            return $(':input', this).clearForm();
        if (type === 'text' || type === 'password' || tag === 'textarea')
            this.value = '';
        else if (type === 'checkbox' || type === 'radio')
            this.checked = false;
        else if (tag === 'select')
            this.selectedIndex = -1;
    });
};


function showChangeUsername() {
    $('form').clearForm();
    $('.change-email, .change-password').hide();
    $('.change-username').show();


}


$(function () {
    var ChangeSettingsModule = (function () {
        var ChangeEmail = {
            form:         $('.change-email').find('form')[0]    || null,
            $container:   $('.change-email')                    || null,
            $requestLink: $('#change-email-link')               || null
        };
        var ChangePassword = {
            form:         $('.change-password').find('form')[0] || null,
            $container:   $('.change-password')                 || null,
            $requestLink: $('#change-password-link')            || null
        };
        var ChangeUsername = {
            form:         $('.change-username').find('form')[0] || null,
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
            transition: function (objToHide, objToShow){
                objToHide.form.reset();

                objToHide.$container.fadeOut('slow', function() {
                    objToShow.$container.fadeIn('slow');
                  });
            },
            showRequestedForm: function (newRequest) {
                if (this.currentRequest == null) {
                    this.show(newRequest);
                    this.currentRequest = newRequest;
                } else if (this.currentRequest != newRequest) {
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
            });
    })();
});
