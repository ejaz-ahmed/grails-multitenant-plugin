<%@ page import="demo.Book" %>



<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'price', 'error')} ">
	<label for="price">
		<g:message code="book.price.label" default="Price" />
		
	</label>
	<g:field name="price" value="${fieldValue(bean: bookInstance, field: 'price')}"/>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'tenantId', 'error')} required">--}%
	%{--<label for="tenantId">--}%
		%{--<g:message code="book.tenantId.label" default="Tenant Id" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:field name="tenantId" type="number" value="${bookInstance.tenantId}" required=""/>--}%

%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="book.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${bookInstance?.title}"/>

</div>

