const commentCreateBtn = document.querySelector("#comment-create-btn");
let today = new Date();
commentCreateBtn.addEventListener("click", function(){
    const comment = {
        username: document.querySelector(".username").value,
        comment: document.querySelector("#new-comment-body").value,
        articleId: document.querySelector("#new-comment-article-id").value,
        postdate: today.toLocaleString()
    }
    console.log(comment);
    const url = "/api/" + comment.articleId + "/comments";
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(comment)
    }).then(response => {
        window.location.reload();
    })
});

const buttons = document.querySelectorAll("#comment-modify-btn");

buttons.forEach(button => {
    button.addEventListener("click", function () {

        const commentId = button.dataset.commentId;
        const commentDiv = document.getElementById(`comments-${commentId}-box`);
        const articleId = commentDiv.querySelector(".articleId").innerText
        const existedComment = commentDiv.querySelector(".card-text");
        const commentBody = existedComment ? existedComment.innerText : "";

        const editFormHtml = `
            <div class="modify mt-2 p-2 border rounded bg-light">
                <form>
                    <div class="d-flex align-items-start gap-2">
                        <textarea class="form-control flex-grow-1" style="height: auto; min-height: 80px;" id="modify-comment-body-${commentId}" rows="2">${commentBody}</textarea>
                        <button class="btn btn-primary comment-modify-complete align-self-start" type="button" data-comment-id="${commentId}" style="white-space: nowrap;">댓글 수정</button>
                    </div>
                </form>
            </div>
        `;

        const existingForm = commentDiv.querySelector(".modify");

        if (existingForm) {
            existingForm.remove();
        } else {
            commentDiv.insertAdjacentHTML("beforeend", editFormHtml);

            // 수정폼 삽입 후 → 새로 생긴 "댓글 수정" 버튼 찾기
            const commentModifyBtn = commentDiv.querySelector(".comment-modify-complete");

            commentModifyBtn.addEventListener("click", function () {

                const comment = {
                    id: commentId,
                    username: commentDiv.querySelector(".username").innerText,
                    comment: commentDiv.querySelector(`#modify-comment-body-${commentId}`).value,
                    articleId: articleId,
                    commentdate: commentDiv.querySelector(".commentdate").innerText
                };

                const url = "/api/" + articleId + "/comments";

                fetch(url, {
                    method: "PATCH",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(comment)
                }).then(response => {
                    window.location.reload();
                });
            });
        }
    });
});
const buttons_delete = document.querySelectorAll("#comment-delete-btn");

buttons_delete.forEach(button => {
    button.addEventListener("click", function () {

        const commentId = button.dataset.commentId;
        const commentDiv = document.getElementById(`comments-${commentId}-box`);
        const articleId = commentDiv.querySelector(".articleId").innerText

        const comment = {
                    id: commentId,
                    username: commentDiv.querySelector(".username").innerText,
                    comment: commentDiv.querySelector(".card-text").value,
                    articleId: articleId,
                    commentdate: commentDiv.querySelector(".commentdate").innerText
                };

                console.log(comment);

                const url = "/api/comments/"+commentId;

                if (confirm("정말 댓글을 삭제하시겠습니까?")){
                fetch(url, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                    //body: JSON.stringify(comment)
                }).then(response => {
                    window.location.reload();
                });
                }
    });
});
