function saludarVisitante() {
    alert("¡Bienvenido a nuestro blog integrador!");
}

window.onload = function() {
    const opciones = { year: 'numeric', month: 'long', day: 'numeric' };
    const el = document.getElementById('fechaActual');
    if (el) el.textContent = new Date().toLocaleDateString('es-ES', opciones);
};