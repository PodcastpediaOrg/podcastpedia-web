<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">   
	$(document).ready(function() {
		
		//initialization stuff '${roundedRatingScore}'
    	$("input#currentRatingDisplay").rating('select', '10');    

    	//$("input#currentRatingDisplay").rating('readOnly', true);
    	$("input#currentRatingDisplay").rating({
    		callback: function(value, link){
    				  //first create recaptcha 
	    		      $.getScript('http://www.google.com/recaptcha/api/js/recaptcha_ajax.js',
	    		              function showRecaptcha() {		 
	    					        Recaptcha.create("6LcW3OASAAAAAKEJTHMmp_bo5kny4lZXeDtgcMqC",
	    					       	    "recaptcha_div",
	    					       	    {
	    					       	      theme: "clean",
	    					       	      callback: Recaptcha.focus_response_field
	    					       	    }
	    					        );
	    		      });    			
	    			//$("#dialog-rating" ).data("rating", value);
	 			    $("#rateIt").rating('select', value, false);
	        		$("#dialog-rating" ).dialog("open");
	        		
    			}
    	});         	
    	
    	//init rating fields
    	var ratingEmail = $("input#rate_email"),
    		ratingName = $("input#rate_name"),
    		subscriptionEmail = $("input#sub_email");
    	
    	//recaptcha init and create
		 var strings = new Array();
		 strings['recaptcha.instructions_visual'] = "<spring:message code='recaptcha.instructions_visual' javaScriptEscape='true'/>";
		 strings['recaptcha.instructions_audio'] = "<spring:message code='recaptcha.instructions_audio' javaScriptEscape='true'/>"; 
		 strings['recaptcha.play_again'] = "<spring:message code='recaptcha.play_again' javaScriptEscape='true'/>";
		 strings['recaptcha.cant_hear_this'] = "<spring:message code='recaptcha.cant_hear_this' javaScriptEscape='true'/>";
		 strings['recaptcha.visual_challenge'] = "<spring:message code='recaptcha.visual_challenge' javaScriptEscape='true'/>";
		 strings['recaptcha.audio_challenge'] = "<spring:message code='recaptcha.audio_challenge' javaScriptEscape='true'/>";
		 strings['recaptcha.refresh_btn'] = "<spring:message code='recaptcha.refresh_btn' javaScriptEscape='true'/>"; 
		 strings['recaptcha.help_btn'] = "<spring:message code='recaptcha.help_btn' javaScriptEscape='true'/>";
		 strings['recaptcha.incorrect_try_again'] = "<spring:message code='recaptcha.incorrect_try_again' javaScriptEscape='true'/>";
		
		 var RecaptchaOptions = {
		    custom_translations : {		 
		         instructions_visual :  strings['recaptcha.instructions_visual'] ,
		         instructions_audio : strings['recaptcha.instructions_audio'],
		         play_again : strings['recaptcha.play_again'],
		         cant_hear_this : strings['recaptcha.cant_hear_this'],
		         visual_challenge : strings['recaptcha.visual_challenge'],
		         audio_challenge : strings['recaptcha.audio_challenge'],
		         refresh_btn : strings['recaptcha.refresh_btn'],
		         help_btn : strings['recaptcha.help_btn'],
		         incorrect_try_again : strings['recaptcha.incorrect_try_again']
		 	},		 
		    theme : 'clean'
		 };   
		 
             	    	
    	//when clicking on subscribe via email, subscribe via email checkbox from comments is checked 
    	$("#subscribeItAnchor").click(function(){
    		$("#subscribe-form" ).dialog("open");
    	});
    	
    	 $("#subscribe-form" ).dialog({
    		 autoOpen: false,
    		 height: 200,
    		 width: 470,
    		 modal: true,
    		 buttons: {
    		 	"Subscribe": function() {
      		        var bValid=true;
      		        subscriptionEmail.removeClass( "input_in_error" );
      		      	$("#subscription_email_err_mess").remove();
      		        bValid = bValid && checkLength( subscriptionEmail, "email", 6, 80 );
      		        bValid = bValid && checkRegexp( subscriptionEmail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "e.g. test@podcastpedia.org");  		        
      		        if(bValid){    		 		
    		 			postSubscription();
      		        } else {
      		        	subscriptionEmail.after("<br/><span id='subscription_email_err_mess' class='error_form_validation'><spring:message code='invalid.required.email'/></span>");
      		        	return false; 
      		        }
    		 	 },
	    		 Cancel: function() { $( this ).dialog( "close" ); }
   			 },    		 
    		 close: function() {
    			 //we reset the values 
    			 subscriptionEmail.val("").removeClass("input_in_error");
    			 $("#subscription_email_err_mess").remove();    			 
    		 }
    	});
    	 
		$( "#dialog-subscribed" ).dialog({
			 autoOpen: false,
     		 height: 150,
    		 width: 450,
  			 modal: true
  			 /* when I add button it doesn't show correctly investigate why...
 	  		 buttons: {
 	  			'Ok': function() {
 	  				$( this ).dialog( "close" );
 	  			}
 	  		 }	
			*/
  		});    	 
    	     
		$( "#dialog-rated" ).dialog({
			 autoOpen: false,
    		 height: 100,
   		 	 width: 400,
 			 modal: true
 			 /* when I add button it doesn't show correctly investigate why...
	  		 buttons: {
	  			'Ok': function() {
	  				$( this ).dialog( "close" );
	  			}
	  		 }	
			*/
 		});
		
        function postSubscription(){
  		  // Call a URL and pass two arguments
  		  // Also pass a call back function
  		  // See http://api.jquery.com/jQuery.post/
  		  // See http://api.jquery.com/jQuery.ajax/
  		  // You might find a warning in Firefox: Warning: Unexpected token in attribute selector: '!'
  		  // See http://bugs.jquery.com/ticket/7535
  		  // ATTENTION - project name must be included in the path 
  		  $.post("/user_interaction/ajax/email_subscription",
  		       {  
  			  		email:  $("input#sub_email").val(),
 		        	podcastId:  $("input#sub_podcastId").val() 
  		        },
  		        function(data){	  		        	
  		        	//we have received valid response 
	  	  			$("#subscribe-form" ).dialog( "close" );
		  	  		$(function() {
		  	  			$( "#dialog-subscribed" ).dialog("open");
		  	  			setTimeout(function() { $( "#dialog-subscribed" ).dialog('close'); }, 5000);  	  			
		  	  		});

  		   		});        	
        }
                
        function checkLength( o, n, min, max ) {
        	if ( o.val().length > max || o.val().length < min ) {
	        	o.addClass( "input_in_error" );        	
	        	return false;
        	} else {
        		return true;
        	}
        }
        
        function checkRegexp( o, regexp, n  ) {
        	if ( !( regexp.test( o.val() ) ) ) {
        		o.addClass( "input_in_error" );
        		return false;
        	} else {
        		return true;
        	}
        }

        //rating stuff
    	 $("#dialog-rating" ).dialog({
    		 autoOpen: false,
    		 height: 450,
    		 width: 750,
    		 modal: true,
    		 buttons: {
    		 	"Rate": function() {
      		        var bValid=true;
      		        //verify name
      		        ratingName.removeClass( "input_in_error" );
      		        $("#rate_nm_err_mess").remove();
      		        bValid = checkLength( ratingName, "name", 1, 80 );
      		        if(!bValid){
      		        	ratingName.after("<span id='rate_nm_err_mess' class='error_form_validation'><spring:message code='invalid.required.name'/></span>");
      		        	return false;
      		        }
      		        //verify email 
      		        ratingEmail.removeClass( "input_in_error" );
      		        $("#rate_em_err_mess").remove();
      		        bValid = checkLength( ratingEmail, "email", 5, 80 );
      		        bValid = bValid && checkRegexp( ratingEmail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "e.g. test@podcastpedia.org");
      		        if(!bValid){
      		        	ratingEmail.after("<span id='rate_em_err_mess' class='error_form_validation'><spring:message code='invalid.required.email'/></span>");
      		        	return false;
      		        }     
      		        //verify rating
      		        var rating = $('input[name=rating]:checked', '#add_rating_form').val();
      		        $("#rate_err_mess").remove();
      		        if(typeof rating === 'undefined'){
      		        	$("#rating_area").after("<span id='rate_err_mess' class='error_form_validation' style='margin:0px 0px 10px 30px;'>Please rate the podcast</span>");
      		        }
      		        if(bValid){
      		        	//$("#err_invalid_recaptcha").remove();
          		        postRating();	
      		        }
    		 	 },
	    		 Cancel: function() { $( this ).dialog( "close" ); }
   			 },    		 
    		 close: function() {
    			 //we reset the values 
    			 ratingEmail.val("").removeClass("input_in_error");
    			 ratingName.val("").removeClass("input_in_error");
    			 Recaptcha.reload();
    			 $("#rateIt").rating('select', 10);
    			 $("#rate_nm_err_mess").remove();
    			 $("#rate_em_err_mess").remove();
    			 $("#rate_err_mess").remove();
    			 $("#err_invalid_recaptcha").remove(); 
    		 }
    	});  
        
         function postRating(){
     		  // Call a URL and pass two arguments
     		  // Also pass a call back function
     		  // See http://api.jquery.com/jQuery.post/
     		  // See http://api.jquery.com/jQuery.ajax/
     		  // You might find a warning in Firefox: Warning: Unexpected token in attribute selector: '!'
     		  // See http://bugs.jquery.com/ticket/7535
     		  // ATTENTION - project name must be included in the path 
     		  $.post("/user_interaction/ajax/rating",
     		     {  email:  ratingEmail.val(),
     		        podcastId:  $("input#rate_podcastId").val(),
     		        episodeId: $("input#rate_episodeId").val(),
     		        name: ratingName.val(),
     		        rating: $('input[name=rating]:checked', '#add_rating_form').val(),//$("input#rateIt:checked").val(),
     		        current_rating: $("input#current_rating").val(),
     		        current_number_ratings: $("input#current_number_ratings").val(),
     		        recaptcha_challenge_field: $("#recaptcha_challenge_field").val(),
     		        recaptcha_response_field: $("input#recaptcha_response_field").val()        		        
     		      },
     		      function(data){	
   		        	  $("#err_invalid_recaptcha").remove();       		    	  
     		    	  if(data.invalidCaptcha == true){   		    		  
     		    		 $("#recaptcha_div").before("<span class='error_form_validation' id='err_invalid_recaptcha' style='margin:0px 0px 10px 30px;'><spring:message code='invalid.captcha' text='Invalid captcha please try again'/></span>");
     		    		 Recaptcha.reload();
     		    	  } else {
     	     		        //was successful
     	     		        $("#nr_ratings").replaceWith("<span id='nr_ratings'>"+ data.numberOfRatings + "</span>")
     	     		        $("input#currentRatingDisplay").rating('readOnly', false);
     	     		        var newRating = eval(data.updatedRating);
     	     		        $("input#currentRatingDisplay").rating('select', newRating);
     	     		        $("input#currentRatingDisplay").rating('readOnly', true);
     	     		        $("#rateItAnchor").remove();
     	     	  			$("#dialog-rating" ).dialog( "close" );
     		   	  	  		$(function() {
     			  	  			$( "#dialog-rated" ).dialog("open");
     			  	  			setTimeout(function() { $( "#dialog-rated" ).dialog('close'); }, 3000);  	  			
     		   	  	  		});  
     		    	  }
     		     });        	
           }            	
    });  
</script>	