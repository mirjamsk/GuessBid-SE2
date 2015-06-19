/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateBid($bidInput) {
    $('.ui-messages-error').fadeOut('fast');
    var bid = $bidInput.val().trim();

    if ((bid !== '') && (!(/^\d+(\.\d{1,2})?$/.test(bid)) || bid === "0")) {
        $bidInput.val('');
        $("#bid-js-messages").fadeIn().text('Please enter a positive number with up to 2 decimal places');
        return false;
    }
    else
        $("#bid-js-messages").text('').fadeOut();

}