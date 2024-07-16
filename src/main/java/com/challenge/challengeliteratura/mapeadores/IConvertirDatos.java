package com.challenge.challengeliteratura.mapeadores;

public interface IConvertirDatos {

	<T> T obtenerDatos(String json, Class<T> clase);

}
