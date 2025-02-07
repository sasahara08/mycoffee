const modal = document.getElementById("modal")

function openModal(button) {
  modal.style.display = "block";

  const beanName = button.getAttribute("data-beanName");
  const roast = button.getAttribute("data-roast");
  const grindSize = button.getAttribute("data-grindSize");
  const beansWeight = button.getAttribute("data-beansWeight");
  const waterVolume = button.getAttribute("data-waterVolume");
  const waterTemp = button.getAttribute("data-waterTemp");
  const steamingTime = button.getAttribute("data-steamingTime");
  const dripper = button.getAttribute("data-dripper");
  const filter = button.getAttribute("data-filter");
  const memo = button.getAttribute("data-memo");
  const commentBlock = document.getElementById("commentList");
  const commentUrl = button.getAttribute("data-comment-url");

  const commentData = button.getAttribute("data-comment");
  const commentJson = JSON.parse(commentData);
  console.log(commentData);

  if (commentJson.length != 0) {
    commentJson.forEach(comment => {
      commentBlock.innerHTML += `
        <div class="commentHeader">
            <img th:src="@{/user_icon/{img}(img=${comment.user.img})}" alt="ユーザーアイコン">
            <span>ユーザー名</span>
          </div>

          <div class="content">
            <span th:text="${comment.text}"></span>
          </div>
          `
    });
  };

  document.getElementById("name").innerText = beanName ? beanName : "未入力";
  document.getElementById("roast").innerText = roast ? roast : "未入力";
  document.getElementById("grindSize").innerText = grindSize ? grindSize : "未入力";
  document.getElementById("beansWeight").innerText = beansWeight ? beansWeight : "未入力";
  document.getElementById("waterVolume").innerText = waterVolume ? waterVolume : "未入力";
  document.getElementById("waterTemp").innerText = waterTemp ? waterTemp : "未入力";
  document.getElementById("steamingTime").innerText = steamingTime ? steamingTime : "未入力";
  document.getElementById("dripper").innerText = dripper ? dripper : "未入力";
  document.getElementById("filter").innerText = filter ? filter : "未入力";
  document.getElementById("memo").innerText = memo ? memo : "未入力";


  const commentForm = document.getElementById("commentForm");
  commentForm.setAttribute('action', (commentUrl));

};

const closeModal = () => {
  modal.style.display = "none";
};

document.addEventListener("click", (e) => {
  if (e.target == modal) {
    modal.style.display = "none";
  }
});

document.addEventListener("click", (e) => {
  if (e.target === modal) {
    closeModal();
  }
});