function validarSistema(event) {
    event.preventDefault(); 
    let id = document.getElementById("sysId").value;
    let nombre = document.getElementById("sysNombre").value;
    
    if (id.length > 5) {
        alert("Error: El ID supera los 5 dígitos permitidos.");
        return;
    }
    alert("Validación exitosa: Sistema '" + nombre + "' registrado correctamente.");
}