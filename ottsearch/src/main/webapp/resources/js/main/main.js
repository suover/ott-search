const popularSwiper = new Swiper('.popular-wrapper', {
  slidesPerView: 5,
  spaceBetween: 60,
    slidesPerGroup: 5,

  observer: true,
  observeParents: true,

  speed: 900,

  autoplay: false,

  navigation: {
    nextEl: ".p-next",
    prevEl: ".p-prev",
  },
});

const upcomingSwiper = new Swiper('.upcoming-wrapper', {
  slidesPerView: 5,
  spaceBetween: 60,
  slidesPerGroup: 5,

  observer: true,
  observeParents: true,

  speed: 900,

  autoplay: false,

  navigation: {
    nextEl: ".u-next",
    prevEl: ".u-prev",
  },
});