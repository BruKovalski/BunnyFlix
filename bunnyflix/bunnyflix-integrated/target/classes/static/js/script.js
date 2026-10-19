let cart = [];

function scrollCarousel(button, direction) {
  const container = button.parentElement;
  const track = container.querySelector('.carousel-track');
  const scrollAmount = 300 * direction;
  track.scrollBy({ left: scrollAmount, behavior: 'smooth' });
}

function addToCart(id, title, price, image) {
  const existing = cart.find(item => item.id === id);
  if (existing) {
    existing.qty++;
  } else {
    cart.push({ id, title, price, image, qty: 1 });
  }
  updateCart();
  toggleCart(true);
}

function updateCart() {
  const badge = document.getElementById('cartBadge');
  const itemsContainer = document.getElementById('cartItems');
  const totalEl = document.getElementById('cartTotal');

  const totalCount = cart.reduce((acc, item) => acc + item.qty, 0);
  badge.innerText = totalCount;

  if (cart.length === 0) {
    itemsContainer.innerHTML = '<p style="color:#888; text-align:center; margin-top:40px;">Seu carrinho está vazio.</p>';
    totalEl.innerText = 'R$ 0,00';
    return;
  }

  itemsContainer.innerHTML = '';
  let grandTotal = 0;

  cart.forEach(item => {
    grandTotal += item.price * item.qty;
    itemsContainer.innerHTML += `
      <div class="cart-item">
        <img src="${item.image}" alt="${item.title}">
        <div class="cart-item-info">
          <h5 style="font-size:13px;">${item.title}</h5>
          <p style="color:#aaa; font-size:12px;">Qtd: ${item.qty} x R$ ${item.price.toFixed(2)}</p>
          <strong style="color:#46d369;">R$ ${(item.price * item.qty).toFixed(2)}</strong>
        </div>
        <button onclick="removeFromCart(${item.id})" style="background:none; border:none; color:#E50914; cursor:pointer;"><i class="fa-solid fa-trash"></i></button>
      </div>
    `;
  });

  totalEl.innerText = `R$ ${grandTotal.toFixed(2)}`;
}

function removeFromCart(id) {
  cart = cart.filter(item => item.id !== id);
  updateCart();
}

function toggleCart(open = null) {
  const drawer = document.getElementById('cartDrawer');
  if (open === true) {
    drawer.classList.add('active');
  } else if (open === false) {
    drawer.classList.remove('active');
  } else {
    drawer.classList.toggle('active');
  }
}

function openModal(title, categoria, especie, preco, avaliacao, duracao, imagem, descricao) {
  document.getElementById('modalTitle').innerText = title;
  document.getElementById('modalCategoria').innerText = categoria;
  document.getElementById('modalEspecie').innerText = especie;
  document.getElementById('modalRating').innerText = `${avaliacao} ⭐ Avaliação`;
  document.getElementById('modalDuracao').innerText = duracao;
  document.getElementById('modalImg').src = imagem;
  document.getElementById('modalDesc').innerText = descricao;

  document.getElementById('modalBuyBtn').onclick = function() {
    addToCart(Date.now(), title, preco, imagem);
    closeModal();
  };

  document.getElementById('modal').classList.add('active');
}

function closeModal() {
  document.getElementById('modal').classList.remove('active');
}

function checkout() {
  if(cart.length === 0) return alert('Seu carrinho está vazio!');
  alert('Pedido realizado com sucesso! Aproveite a experiência BunnyFlix 🐰🍿');
  cart = [];
  updateCart();
  toggleCart(false);
}

window.addEventListener('scroll', () => {
  const nav = document.getElementById('navbar');
  if (window.scrollY > 50) {
    nav.classList.add('scrolled');
  } else {
    nav.classList.remove('scrolled');
  }
});
