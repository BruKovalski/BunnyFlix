/* BunnyFlix – tema claro/escuro, menu móvel, filtros, imagens e animações de rolagem */
(function () {
  'use strict';

  var raiz = document.documentElement;
  var CHAVE_TEMA = 'bunnyflix-tema';
  var movimentoReduzido = window.matchMedia && matchMedia('(prefers-reduced-motion: reduce)').matches;

  /* ---------- tema claro / escuro ---------- */
  function lerTemaSalvo() {
    try {
      var t = localStorage.getItem(CHAVE_TEMA);
      return t === 'dark' || t === 'light' ? t : null;
    } catch (e) { return null; }
  }

  function temaDoSistema() {
    return window.matchMedia && matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

  function aplicarTema(tema) {
    raiz.setAttribute('data-theme', tema);
    var escuro = tema === 'dark';
    var botao = document.getElementById('temaBtn');
    var rotulo = document.getElementById('temaRotulo');
    if (botao) {
      botao.setAttribute('aria-pressed', String(escuro));
      botao.title = escuro ? 'Voltar ao modo claro' : 'Ativar modo escuro';
    }
    if (rotulo) { rotulo.textContent = escuro ? 'Voltar ao modo claro' : 'Ativar modo escuro'; }
    var meta = document.querySelector('meta[name="theme-color"]');
    if (meta) { meta.setAttribute('content', escuro ? '#172022' : '#1f5f5b'); }
  }

  var botaoTema = document.getElementById('temaBtn');
  aplicarTema(raiz.getAttribute('data-theme') || temaDoSistema());

  if (botaoTema) {
    botaoTema.addEventListener('click', function () {
      var novo = raiz.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
      if (!movimentoReduzido) {
        raiz.classList.add('theme-anim');
        window.setTimeout(function () { raiz.classList.remove('theme-anim'); }, 450);
      }
      aplicarTema(novo);
      try { localStorage.setItem(CHAVE_TEMA, novo); } catch (e) { /* armazenamento indisponível */ }
    });
  }

  // Sem escolha salva, acompanha o sistema operacional; entre abas, acompanha a escolha mais recente.
  if (window.matchMedia) {
    var consulta = matchMedia('(prefers-color-scheme: dark)');
    var aoMudarSistema = function (e) { if (!lerTemaSalvo()) { aplicarTema(e.matches ? 'dark' : 'light'); } };
    if (consulta.addEventListener) { consulta.addEventListener('change', aoMudarSistema); }
  }
  window.addEventListener('storage', function (e) {
    if (e.key === CHAVE_TEMA) { aplicarTema(lerTemaSalvo() || temaDoSistema()); }
  });

  /* ---------- menu móvel e filtros ---------- */
  function configurarAlternador(botao, alvo) {
    if (!botao || !alvo) { return; }

    function definir(aberto) {
      alvo.classList.toggle('open', aberto);
      botao.setAttribute('aria-expanded', String(aberto));
      var icone = botao.querySelector('i');
      if (icone && botao.id === 'menuBtn') {
        icone.className = aberto ? 'fa-solid fa-xmark' : 'fa-solid fa-bars';
      }
    }

    botao.addEventListener('click', function () {
      definir(botao.getAttribute('aria-expanded') !== 'true');
    });

    document.addEventListener('keydown', function (evento) {
      if (evento.key === 'Escape' && botao.getAttribute('aria-expanded') === 'true') {
        definir(false);
        botao.focus();
      }
    });
  }

  configurarAlternador(document.getElementById('menuBtn'), document.getElementById('menuPrincipal'));
  configurarAlternador(document.getElementById('filtrosBtn'), document.getElementById('filtros'));

  /* ---------- sombra do cabeçalho ao rolar ---------- */
  var cabecalho = document.querySelector('.site-header');
  if (cabecalho) {
    var marcarRolagem = function () { cabecalho.classList.toggle('scrolled', window.scrollY > 8); };
    window.addEventListener('scroll', marcarRolagem, { passive: true });
    marcarRolagem();
  }

  /* ---------- imagens: aparecem suavemente e têm alternativa se falharem ---------- */
  function imagemCarregada(img) { img.classList.add('loaded'); }

  function imagemFalhou(img) {
    var alternativa = img.getAttribute('data-fallback');
    if (alternativa && img.getAttribute('src') !== alternativa) {
      img.classList.add('ilustracao');
      img.setAttribute('src', alternativa);   // ilustração padrão no lugar da foto que não abriu
      img.removeAttribute('data-fallback');
    } else {
      img.remove();                           // sem alternativa: fica o ícone que já está no cartão
    }
  }

  function prepararImagem(img) {
    img.setAttribute('data-fade', '');
    img.addEventListener('load', function () { imagemCarregada(img); });
    img.addEventListener('error', function () { imagemFalhou(img); });
    if (img.complete) {
      if (img.naturalWidth > 0) { imagemCarregada(img); } else { imagemFalhou(img); }
    }
  }

  document.querySelectorAll('.card-media img, .species-media img, .group-media img, .detail-media img')
    .forEach(prepararImagem);

  /* ---------- revelação ao rolar ---------- */
  var seletor = '.section-head, .group-card, .species-grid > li, .shortcut, .product-card, ' +
                '.about-strip > *, .detail-media, .detail-info, .results-count, .filters, .prose > *, .contact-list > div';
  var itens = document.querySelectorAll(seletor);

  if (movimentoReduzido || !('IntersectionObserver' in window)) { return; }

  var observador = new IntersectionObserver(function (entradas) {
    entradas.forEach(function (entrada) {
      if (entrada.isIntersecting) {
        entrada.target.classList.add('in');
        observador.unobserve(entrada.target);
      }
    });
  }, { threshold: 0.12, rootMargin: '0px 0px -40px 0px' });

  var contagemPorPai = new Map();
  itens.forEach(function (el) {
    var n = contagemPorPai.get(el.parentNode) || 0;
    contagemPorPai.set(el.parentNode, n + 1);
    el.style.setProperty('--delay', (Math.min(n, 7) * 0.06).toFixed(2) + 's');
    el.classList.add('reveal');
    observador.observe(el);
  });
})();
