/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validateBid($bidInput){
    console.log($bidInput);
    if(! /^\d+\.{0,1}\d{0,2}$/.test($bidInput.val()))
        $bidInput.val('');
}