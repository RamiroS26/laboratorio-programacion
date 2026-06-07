function mostrarAlertaTramite() {
    let tipo = document.getElementById("tipoTramite").value;
    if (tipo === "Urgente") alert("Resolución dentro de las 24 horas");
    else if (tipo === "Normal") alert("Resolución dentro de las 48 horas");
    else if (tipo === "Bajo") alert("Resolución dentro de las 96 horas");
}

function validarResponsable(input) {
    input.value = input.value.toUpperCase();
    if (/\d/.test(input.value)) {
        document.getElementById("errResponsable").style.display = "block";
        input.value = input.value.replace(/\d/g, ''); 
    } else {
        document.getElementById("errResponsable").style.display = "none";
    }
}

function ocultarErrores() {
    document.querySelectorAll('.error').forEach(e => e.style.display = 'none');
    document.getElementById('resultado-estadistica').style.display = 'none';
    document.getElementById('resultado-json').style.display = 'none';
}

function validarFormulario() {
    ocultarErrores();
    let esValido = true;

    let expObj = document.getElementById("nroExpediente");
    let expVal = expObj.value;
    if (expVal.includes("EXP-")) expVal = expVal.replace("EXP-", "").split("/")[0];

    if (!/^\d{4}$/.test(expVal)) {
        document.getElementById("errExpediente").style.display = "block";
        esValido = false;
    } else {
        expObj.value = `EXP-${expVal}/25`;
    }

    let dias = document.getElementById("diasTramite").value;
    if (dias === "" || parseInt(dias) <= 0) {
        document.getElementById("errDias").style.display = "block";
        esValido = false;
    }

    if (!document.querySelector('input[name="estado"]:checked')) {
        document.getElementById("errEstado").style.display = "block";
        esValido = false;
    }

    let resp = document.getElementById("responsable").value;
    if (resp.trim() === "") {
        document.getElementById("errResponsable").style.display = "block";
        esValido = false;
    }

    let expHoy = document.getElementById("expHoy").value;
    if (expHoy === "") {
        document.getElementById("errExpHoy").style.display = "block";
        esValido = false;
    }

    let horas = document.getElementById("horasTrabajadas").value;
    if (horas === "" || parseInt(horas) < 1 || parseInt(horas) > 12) {
        document.getElementById("errHoras").style.display = "block";
        esValido = false;
    }

    if (esValido) alert("Expediente validado correctamente");
    return esValido;
}

function calcularEstadistica() {
    let expedientes = parseFloat(document.getElementById("expHoy").value);
    let horas = parseFloat(document.getElementById("horasTrabajadas").value);
    
    if (isNaN(expedientes) || isNaN(horas)) return alert("Valide el formulario primero.");
    
    let productividad = expedientes / horas;
    let nivel = productividad < 2 ? "Productividad baja" : (productividad <= 5 ? "Productividad media" : "Productividad alta - Buen rendimiento");

    let div = document.getElementById("resultado-estadistica");
    div.style.display = "block";
    div.innerHTML = `<strong>Productividad:</strong> ${productividad.toFixed(2)} → ${nivel}`;
}

function generarJSON() {
    let datos = {
        numeroExpediente: document.getElementById("nroExpediente").value,
        area: document.getElementById("area").value,
        tipoTramite: document.getElementById("tipoTramite").value,
        diasEnTramite: document.getElementById("diasTramite").value,
        estado: document.querySelector('input[name="estado"]:checked')?.value || "",
        responsable: document.getElementById("responsable").value,
        expedientesHoy: document.getElementById("expHoy").value,
        horasTrabajadas: document.getElementById("horasTrabajadas").value
    };

    document.getElementById("resultado-json").style.display = "block";
    document.getElementById("jsonOutput").textContent = JSON.stringify(datos, null, 4);
}