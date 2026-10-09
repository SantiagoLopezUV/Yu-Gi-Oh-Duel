<div align="center">

# YGO-Battle
<br/>


<br/>

**mini-aplicación de escritorio en Java Swing que simula un combate estilo Pokémon Stadium entre dos Pokémon obtenidos en vivo desde PokeAPI [PokeAPI](https://pokeapi.co/).**

</div>

---

## Descripción

**YGO-Battle** es un proyecto académico que consulta datos de cartas de Yu-Gi-Oh! en vivo desde la API pública YGOProDeck y los utiliza para simular un duelo simplificado entre un jugador y la máquina mediante una interfaz gráfica de escritorio construida en Java Swing.   
Los datos (nombre, tipo, ATK, DEF e imagen oficial) no están almacenados localmente; se solicitan en tiempo real a la API mediante peticiones asíncronas cada vez que se genera un mazo de combate

## Funcionalidades

- **Carga de mazos asíncrona:** Obtención aleatoria de 3 cartas de tipo _Monster_ para el jugador y 3 para la máquina al iniciar el duelo.   
- **Visualización de cartas:** Muestra la imagen oficial, el nombre y las estadísticas base (ATK y DEF) del jugador y la máquina.
- **Filtro de cartas válido:** Validación de respuesta de la API para descartar cartas Mágicas o de Trampa e insistir hasta obtener exclusivamente Monstruos.
- **Sistema de duelo por rondas:** Enfrentamiento por turnos donde el jugador selecciona una carta y la máquina responde al azar, comparando estadísticas de ATK/DEF al mejor de 3 rondas.
- **Revelación dinámica:** Volteado visual de la carta oculta de la máquina en el momento exacto en que es jugada.
- **Log de batalla desplazable:** Registro en tiempo real de las jugadas, resultados de cada turno, puntaje acumulado y anuncio del ganador final.    
- **Manejo visible de errores:** Alertas en pantalla ante fallos de conexión o respuestas no válidas del servidor.

## Herramientas y tecnologías

<div align="center">

<a href="https://www.jetbrains.com/idea/"><img src="https://github.com/devicons/devicon/blob/master/icons/intellij/intellij-original.svg" title="IntelliJ IDEA" alt="IntelliJ IDEA" width="48" height="48"/></a>
<a href="https://www.java.com/"><img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original.svg" title="Java" alt="Java" width="48" height="48"/></a>
<a href="https://docs.oracle.com/javase/tutorial/uiswing/"><img src="https://img.shields.io/badge/Swing-GUI-007396?style=for-the-badge" title="Java Swing" alt="Java Swing" width="48" height="48"/></a>

</div>

<div align="center">


| **Herramienta**     | **Uso en el proyecto**                                                                              |
| ------------------- | --------------------------------------------------------------------------------------------------- |
| **IntelliJ IDEA**   | IDE para el desarrollo de la aplicación y organización de paquetes.                                 |
| **Java**            | Lenguaje de programación principal (JDK 17 o superior).                                             |
| **Swing**           | Construcción de la interfaz gráfica de escritorio y procesamiento en segundo plano (`SwingWorker`). |
| **YGOProDeck API**  | API REST pública para consultar datos e imágenes de cartas de Yu-Gi-Oh!.                            |
| **`org.json`**      | Lectura y procesamiento de las respuestas en formato JSON.                                          |
| **`java.net.http`** | Cliente HTTP nativo (`HttpClient`) configurado para seguir redirecciones (HTTP 301).                |

</div>

## Uso de PokeAPI

El proyecto consume el endpoint de cartas aleatorias de la API pública YGOProDeck:

|**Endpoint**|**Datos que se usan**|
|---|---|
|`GET [https://db.ygoprodeck.com/api/v7/randomcard.php](https://db.ygoprodeck.com/api/v7/randomcard.php)`|`id`, `name`, `type`, `atk`, `def`, `card_images`|


Si el nombre no existe, la API responde con código `404` y la aplicación muestra un mensaje de error.

## Estructura del proyecto

```
YGO-Battle/
├── README.md
├── screenshots/
└── src/
    ├── api/                        
    │   ├── LoadCard.java           # reintentos y filtro de monstruos
    │   ├── YgoApiClient.java       # Consumo HTTP 
    │   └── YgoApiParser.java       # Lectura del JSON y mapeo a la entidad Card
    ├── battle/
    │   ├── Duel.java               # Lógica del duelo 
    │   └── BattleListener.java     # Interfaz Observer para notificar eventos a la GUI
    ├── exceptions/
    │   └── CardException.java      # Excepciones personalizadas para errores de juego y red
    ├── libs/
    │   └── json-20230227.jar       # Librería org.json
    ├── model/
    │   └── Card.java               # Modelo de datos de la carta
    ├── ui/
    │   └── YgoDuelFrame.java       # Ventana principal Swing, listeners y renderizado
    └── Main.java                   # Punto de entrada de la aplicación
```

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/YGO-Battle.git
   ```
2. Ábrelo con **IntelliJ IDEA**.
3. Verifica que la librería org.json esté vinculada en el proyecto (File → Project Structure → Libraries). Si no aparece, agrega el archivo `src/libs/json-20230227.jar`.
4. Ejecuta la clase `Main`.
5. Haz clic en el botón "Iniciar Duelo (Cargar Mazos)" para solicitar las cartas a la API.
6. Selecciona una de tus cartas disponibles mediante los botones "Elegir Carta" para disputar las rondas contra la máquina

> **Requisitos:** JDK 22 o superior y conexión a internet.

## Capturas


## Equipo de desarrollo

<div align="center">

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/SantiagoLopezUV">
        <img src="https://github.com/SantiagoLopezUV.png?size=120" width="120" height="120" style="border-radius:50%" alt="Santiago Lopez"/><br/>
        <sub><b>Santiago Lopez</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ospina27">
        <img src="https://github.com/ospina27.png?size=120" width="120" height="120" style="border-radius:50%" alt="Alejandro Ospina"/><br/>
        <sub><b>Alejandro Ospina</b></sub>
      </a>
    </td>
  </tr>
</table>

</div>

## Créditos y Aviso Legal

- **Datos e Imágenes:** Datos de cartas e imágenes oficiales proporcionados por la API pública de [YGOProDeck](https://db.ygoprodeck.com/).
- **Propiedad Intelectual:** *Yu-Gi-Oh!*, el diseño del juego de cartas (TCG/OCG) y todos los nombres e imágenes de los personajes/monstruos son marcas registradas y derechos de autor pertenecientes a **Konami Digital Entertainment**, **Kazuki Takahashi / Studio Dice**, **SHUEISHA** y **TV TOKYO**.
- **Aviso de Responsabilidad:** Este es un proyecto estrictamente educativo y de demostración académica, sin fines de lucro. No cuenta con afiliación, respaldo ni patrocinio por parte de Konami, YGOProDeck ni sus entidades asociadas.
