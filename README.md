# Solución ingenua: Reconstrucción de cadenas

## 1. Descripción general

La función `reconstruirCadenaIngenuo` tiene como objetivo reconstruir una cadena de caracteres desconocida utilizando un oráculo.

Recibe dos parámetros:

- `n: Int`: longitud de la cadena original que se desea reconstruir.
- `o: Oraculo`: función que permite comprobar si una cadena candidata corresponde a la cadena original.

La estrategia consiste en generar todas las cadenas posibles de longitud `n`, utilizando los caracteres disponibles en el alfabeto, y consultar al oráculo hasta encontrar una coincidencia.

Es una solución basada en búsqueda exhaustiva, ya que no utiliza optimizaciones para reducir la cantidad de candidatos.

## 2. Estructuras de datos utilizadas

### 2.1. Seq[Char]

Se utiliza para representar las cadenas de caracteres.

La variable `actual` almacena la cadena candidata que se construye durante la búsqueda.

Por ejemplo:

```scala
val actual = Seq('a', 'b')
val nueva = actual :+ 'c'
```

El resultado de `nueva` es `Seq('a', 'b', 'c')`.

El operador `:+` agrega un elemento al final de la secuencia sin modificar la original. Esto permite trabajar con colecciones inmutables.

### 2.2. Alfabeto

Es una secuencia de caracteres que contiene los símbolos permitidos para construir las cadenas candidatas.

Se utiliza mediante `map` para generar una nueva posibilidad por cada carácter disponible.

### 2.3. Option[Seq[Char]]

Es el tipo de retorno de la función auxiliar `buscar`.

Representa dos posibles resultados:

- `Some(resultado)`: se encontró una cadena válida.
- `None`: la cadena candidata no corresponde a la original.

El uso de `Option` permite representar explícitamente el éxito o fracaso de una búsqueda sin utilizar valores especiales.

### 2.4. Seq[Option[Seq[Char]]]

La variable `intentos` almacena los resultados de las llamadas recursivas realizadas para cada carácter del alfabeto.

Cada posición representa el resultado de explorar una de las posibles extensiones de la cadena actual.

## 3. Funcionamiento del algoritmo

La implementación contiene una función principal y una función auxiliar recursiva.

### 3.1. Función principal

```scala
def reconstruirCadenaIngenuo(
    n: Int,
    o: Oraculo
): Seq[Char] = {
    // ...
}
```

Recibe la longitud deseada y el oráculo.

Su responsabilidad es iniciar la búsqueda con una secuencia vacía:

```scala
buscar(Seq()).get
```

La secuencia vacía representa el estado inicial, porque todavía no se ha seleccionado ningún carácter.

El método `get` extrae el resultado contenido en `Some`. Si la búsqueda devuelve `None`, produce una excepción.

### 3.2. Función auxiliar buscar

```scala
def buscar(actual: Seq[Char]): Option[Seq[Char]]
```

Es la función encargada de construir y comprobar las cadenas candidatas.

Tiene dos situaciones principales.

**Caso base:**

```scala
if (actual.length == n) {
    if (o(actual)) Some(actual)
    else None
}
```

Se alcanza cuando la cadena candidata tiene la longitud solicitada.

En ese momento se consulta al oráculo:

- Si devuelve `true`, la función retorna `Some(actual)`.
- Si devuelve `false`, retorna `None`.

**Caso recursivo:**

```scala
else {
    val intentos = alfabeto.map { letra =>
        buscar(actual :+ letra)
    }

    intentos.collectFirst {
        case Some(resultado) => resultado
    }
}
```

Si todavía no se alcanza la longitud deseada, se generan nuevas posibilidades.

Para cada carácter del alfabeto:

1. Se agrega el carácter a la secuencia actual.
2. Se realiza una llamada recursiva con la nueva secuencia.
3. Se almacenan los resultados en `intentos`.
4. Se selecciona el primer resultado que contiene `Some`.

Es importante destacar que `map` sobre una secuencia estricta evalúa todas las llamadas recursivas antes de ejecutar `collectFirst`. Por ello, encontrar una solución no detiene anticipadamente la exploración de las demás ramas.

## 4. Ejemplo de ejecución

Supongamos que:

- Alfabeto: `{a, b}`.
- Longitud solicitada: `3`.
- Cadena original: `aba`.

El algoritmo comienza con una secuencia vacía:

```scala
buscar(Seq())
```

Después genera las posibilidades:

```text
Nivel 0:       ""

Nivel 1:       a          b

Nivel 2:      aa   ab    ba   bb

Nivel 3:     aaa aab aba abb baa bab bba bbb
```

Cuando se generan las cadenas de longitud tres, se consulta al oráculo.

| Cadena candidata | Resultado del oráculo |
|---|---|
| aaa | false |
| aab | false |
| aba | true |
| abb | false |
| baa | false |
| bab | false |
| bba | false |
| bbb | false |

La función devuelve:

```scala
Some(Seq('a', 'b', 'a'))
```

Finalmente, `.get` extrae la secuencia y la función principal devuelve:

```scala
Seq('a', 'b', 'a')
```

## 5. Análisis del algoritmo

### 5.1. Complejidad temporal

Sea:

- `k`: cantidad de caracteres del alfabeto.
- `n`: longitud de la cadena original.

El número de cadenas posibles de longitud `n` es:

\[
k^n
\]

Por lo tanto, el oráculo se consulta \(k^n\) veces, suponiendo que la búsqueda termina normalmente y que todas las cadenas candidatas son evaluadas.

La cantidad total de nodos del árbol implícito de búsqueda es:

\[
1+k+k^2+\cdots+k^n
\]

Para un alfabeto con más de un carácter:

\[
O(k^n)
\]

Esta complejidad representa el crecimiento exponencial de la búsqueda.

La construcción de secuencias también tiene un costo que depende de la implementación concreta de `Seq` y de la operación `:+`.

### 5.2. Complejidad espacial

La búsqueda utiliza recursión con una profundidad máxima de `n`.

Además, cada nivel construye una colección `intentos` que almacena los resultados de sus llamadas recursivas.

Por tanto, existe consumo de memoria asociado a la profundidad de recursión, las colecciones intermedias y las secuencias candidatas.

### 5.3. Condiciones de terminación

La recursión termina cuando:

```scala
actual.length == n
```

En ese momento deja de generar caracteres y consulta al oráculo.

Se presupone que `n` es un entero no negativo y que existe una cadena válida dentro del alfabeto proporcionado.

### 5.4. Limitaciones

- Genera todas las posibilidades de longitud `n`.
- No descarta candidatos antes de alcanzar la longitud final.
- Su tiempo de ejecución crece exponencialmente.
- Utiliza `map` estricto, por lo que no detiene anticipadamente las llamadas recursivas cuando encuentra una solución.
- El uso de `.get` puede provocar una excepción si no existe una solución.

## 6. Conceptos de programación funcional utilizados

| Concepto | Aplicación |
|---|---|
| Inmutabilidad | Las secuencias no se modifican directamente. |
| Recursión | `buscar` se llama a sí misma para construir cadenas. |
| Funciones de orden superior | Se utiliza `map` para generar posibilidades. |
| Reconocimiento de patrones | `collectFirst` utiliza `case Some(resultado)`. |
| Tipos algebraicos | `Option` representa la presencia o ausencia de una solución. |
| Funciones auxiliares | `buscar` encapsula la lógica de búsqueda. |

## 7. Posibles preguntas de sustentación

**¿Por qué se considera un algoritmo ingenuo?**

Porque genera todas las cadenas posibles de longitud `n` y consulta al oráculo sin aprovechar propiedades que permitan reducir las posibilidades.

**¿Qué estructura de datos utiliza para construir las cadenas?**

Utiliza `Seq[Char]`, una secuencia de caracteres inmutable.

**¿Para qué sirve Option?**

Permite representar si una búsqueda encontró una solución (`Some`) o no encontró ninguna (`None`).

**¿Qué hace el operador :+?**

Agrega un carácter al final de la secuencia y produce una nueva secuencia.

**¿Cuál es el caso base de la recursión?**

Cuando la longitud de la cadena candidata es igual a `n`. En ese momento se consulta al oráculo.

**¿Por qué utiliza recursión?**

Porque permite construir las cadenas progresivamente, agregando un carácter en cada llamada hasta alcanzar la longitud deseada.

**¿Qué representa el árbol de posibilidades?**

Representa todas las combinaciones que pueden construirse con los caracteres del alfabeto. Cada nivel corresponde a una posición de la cadena.

**¿Por qué el algoritmo es exponencial?**

Porque si existen `k` caracteres posibles y se necesitan `n` posiciones, se generan `k^n` cadenas candidatas.

**¿El algoritmo se detiene inmediatamente al encontrar una solución?**

No. `map` evalúa todas las llamadas recursivas antes de que `collectFirst` seleccione el primer resultado exitoso.

**¿Qué pasaría si no se encuentra una solución?**

La búsqueda devuelve `None` y el método `.get` provoca una excepción.

## 8. Conclusión

La solución ingenua permite reconstruir una cadena mediante la generación exhaustiva de todas las combinaciones posibles de caracteres.

Su implementación utiliza recursión, secuencias inmutables, funciones de orden superior y el tipo `Option`.

Aunque permite resolver el problema de manera directa, presenta una complejidad exponencial que limita su eficiencia para cadenas de mayor longitud.

Esta implementación constituye la base para desarrollar las siguientes versiones del proyecto, que buscan reducir las consultas al oráculo y mejorar el rendimiento de la reconstrucción.
