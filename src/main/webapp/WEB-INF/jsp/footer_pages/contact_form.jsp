<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	
<c:if test="${thank_you_message != null }"> 
	 <div id="thank_you_message" class="common_radius bg_color shadowy common_mar_pad">		
	 	<spring:message code="contact.thank_you" text="Thank you for your message. We will get back to you as soon as possible."/>		
	 </div>
</c:if> 

<div id="contact_form_wrapper" class="bg_color border_radius shadowy common_mar_pad">
	<h2 class="title_before_form"><spring:message code="contact.header" text="Contact us"/> </h2> 
	<hr class="before_form_header_line"/>
	<div id="contact_follow_us">
		<!-- AddThis Follow BEGIN -->
		<p></p>
		<div class="addthis_toolbox addthis_32x32_style addthis_default_style">
		<a class="addthis_button_facebook_follow" addthis:userid="podcastpedia"></a>
		<a class="addthis_button_twitter_follow" addthis:userid="podcastpedia"></a>
		<a class="addthis_button_google_follow" addthis:userid="101757667729624824161"></a>		
		<a class="addthis_button_rss_follow" addthis:userid="http://www.podcastpedia.org/feeds/newest.rss"></a>
		</div>
		<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=xa-518e3b5a03c67761"></script>
		<!-- AddThis Follow END -->
	</div>
		
    <c:url value="/contact.html" var="addComment_url"/>	
	<form:form name="contact_form" class="vertical_style_form" 
		method="POST" modelAttribute="contactForm" action="${addComment_url}"> 
		
		<div id="label_above_elements">
			<label for="name" class="label">
				<spring:message code="label.name" text="Name - required"/>
			</label> 			
		</div>		
		<p>
			<form:input path="name" id="name" class="form_input"/>
			<form:errors path="name" cssClass="error_form_validation"/>				
		</p>
		
		<div id="label_above_elements">
			<label for="email" class="label">
				<spring:message code="label.email" text="Email - required, verified but never shown"/>
			</label>
		</div>									
		<p>			
			<form:input path="email" id="email" class="form_input"/>	
			<form:errors path="email" cssClass="error_form_validation"/>	
		</p>
		
		<div id="label_above_elements">		
			<label for="topic" class="label">
				<spring:message code="contact.topic" text="Topic"/> 
			</label>
		</div>	
		<p>
			<form:select path="topic" multiple="false" id="topic" class="form_input">
				<c:forEach items="${topics}" var="topic">
					<form:option value="${topic}">
						<spring:message code="${topic}"/>
					</form:option>
				</c:forEach>						
			</form:select>	
		</p>						 		
				
		<div id="label_above_elements">
			<label for="message" class="label">
				<spring:message code="contact.your_message" text="Your message"/>
			</label>
			<form:errors path="message" cssClass="error_form_validation"/>					
		</div>			
		<p id="contact_form_text_message">			
			<form:textarea path="message" rows="5" cols="30" class="form_input url_in"/>		
		</p>
		<spring:message var="send_mess_btn" code="contact.send_message" text="Post comment"/>
		<input type="submit" value="${send_mess_btn}"  id="send_message" class="submit_form_button"/>
		<div class="clear"></div>
	</form:form>
</div>