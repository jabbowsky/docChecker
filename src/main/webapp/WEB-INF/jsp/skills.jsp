<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container">
        <c:forEach items="${skillWords}" var="skillWords">
        <div class="object-and-buttons all-skills" id="skill_${skillWords.id}" >
            <div class="all-skill-words skill-word-deleted-${skillWords.delete} saved-${skillWords.saved}">${skillWords.name}
                <div class="hidden-controls" id="controls_skill_${skillWords.id}" >
                    <a href="/delete-skill-perm?id=${skillWords.id}" >
                        <img src="img/remove-perm-btn.gif" class="skill-db-button" />
                    </a>
                </div>
            </div>
        </div>
        </c:forEach>
		<div>
			<a href="/save-skills"><img src="img/save.gif" class="skill-db-button" />Save</a>
			<a href="/refresh-skills" ><img src="img/refresh.gif" class="skill-db-button" />Refresh</a>
		</div>

	</div>
<%@ include file="common/footer.jspf" %>