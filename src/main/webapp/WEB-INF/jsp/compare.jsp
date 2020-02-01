<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

        <div class="container skills">
                <caption>Your skills are</caption>
                    <a href="/clear-skills" ><img src="img/x2-btn.gif" class="any-button" />Clear skills</a>
                    <c:forEach items="${skills}" var="skill">
                        <div class="object-and-buttons" id="skill_${skill.id}" >
                            <div class="skill color_${skill.id}" >${skill.name}</div>
                            <div class="hidden-controls" id="controls_skill_${skill.id}" >
                                <a href="/delete-skill?id=${skill.id}" >
                                    <img src="img/remove-btn.gif" class="delete-skill-button" />
                                </a>
                            </div>
                        </div>
                    </c:forEach>
            <div>
                <form:form method="post" modelAttribute="skill" autocomplete="off" >
                    <form:hidden path="id" />
                    <fieldset class="form-group">
                        <form:label path="name">Skill list</form:label>
                        <form:input path="name" type="text" onfocus="this.value=''" class="form-control" required="required" />
                    </fieldset>

                    <button type="submit" class="btn btn-success">Add</button>
                </form:form>
            </div>
        </div>
        <div class="container docs">
            <div class="hidden-to-show"></div>
            <c:forEach items="${docs}" var="document">
                <div class="container document">
                    <div class="object-and-buttons" id="doc_${document.id}" >
                        <div class="h3 doc-header">${document.header}</div>
                        <div class="hidden-controls" id="controls_doc_${document.id}" >
                            <a href="/delete-document?id=${document.id}" >
                                <img src="img/remove-btn.gif" class="delete-doc-button" />
                            </a>
                        </div>
                    </div>
                    <div class="document-text">${document.text}</div>
                    <a href="/find-skills-in-document?id=${document.id}"" >
                           <img src="img/find-skills-btn.gif" class="delete-skill-button" />
                    </a>
                    <c:forEach items="${document.skills}" var="docskill">
                        <div id="doc-skill-30" class="skill color_30" >${docskill.name}</div>
                    </c:forEach>
                </div>
            </c:forEach>
            <div class="container document new">
                <form:form method="post" modelAttribute="document" autocomplete="off" >
                    <form:hidden path="id" />
                    <fieldset class="form-group">
                        <form:label path="header">Name doc</form:label>
                        <form:input path="header" type="text" onfocus="this.value=''" class="form-control" required="required" />
                        <form:label path="text">Document text</form:label>
                        <form:input path="text" type="textarea" onfocus="this.value=''" class="form-control" required="required" />
                    </fieldset>

                    <button type="submit" class="btn btn-success">Add</button>
                </form:form>
            </div>
        </div>
<%@ include file="common/footer.jspf" %>