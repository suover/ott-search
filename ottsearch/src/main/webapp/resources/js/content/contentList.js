// 페이지 로드시 카드들이 한장씩 나타나는 효과
// 각 카드에 대해 100ms 간격으로 애니메이션 적용
document.addEventListener("DOMContentLoaded", function() {
  const cards = document.querySelectorAll(".card");
  cards.forEach((card, index) => {
    setTimeout(() => {
      card.classList.add("show");
    }, 100 * index);
  });
});
