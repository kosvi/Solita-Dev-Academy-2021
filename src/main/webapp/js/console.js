// here are the commands

class Help {
  run(virtualConsole, params) {
    virtualConsole.addLine("List of available commands");
    virtualConsole.addLine(" help      - prints this help");
    virtualConsole.addLine(" clear     - clears screen");
    virtualConsole.addLine(" names     - get name information");
    virtualConsole.addLine("");
  }
}

class Names {

  run(virtualConsole, params) {
    switch (params[0]) {
      case "-n":
        this.printNamesAlpabetically(virtualConsole);
        break;
      default:
        this.printHelp(virtualConsole);
        break;
    }
  }

  async printNamesAlpabetically(virtualConsole) {
    var names;
    try {
      names = await NamesApi.getNamesAlphabetically();
    } catch (error) {
      names = null;
    }
    if (names != null) {
      for (let i = 0; i < names.names.length; i++) {
        virtualConsole.addLine("  " + names.names[i]);
      }
    }
  }

  printHelp(virtualConsole) {
    virtualConsole.addLine("Usage: names [OPTIONS] [expression]");
    virtualConsole.addLine(" Options: ");
    virtualConsole.addLine("   -a      - get amounts");
    virtualConsole.addLine("    all      all names & amounts");
    virtualConsole.addLine("    name     single name & amount");
    virtualConsole.addLine("    total    total amoutn of all names");
    virtualConsole.addLine("   -n      - get names");
    virtualConsole.addLine("");
  }
}

// This is the main class that creates the "console" for our webpage
class Console {

  static MAX_NUM_OF_LINES = 50;

  constructor() {
    this.output = document.getElementById("output");
    // output prints lines from an array
    this.lines = Array();
    this.input = document.getElementById("input");
    // input it three separate parts:
    this.inputSign = document.getElementById("inputSign");
    this.inputField = document.getElementById("inputField");
    this.inputCursor = document.getElementById("inputCursor");
    // this is the actual input
    this.hiddenRealInput = document.getElementById("hiddenInput");
    // set some symbol for commandline:
    this.inputSign.innerText = "> ";
    // add all commands:
    this.commands = { help: new Help(), names: new Names() };
    // blinking cursor
    this.inputTimer = setInterval(() => {
      // make cursor blink
      if (this.inputCursor.innerText === "_") {
        this.inputCursor.innerText = "";
      } else {
        this.inputCursor.innerText = "_";
      }
      // and keep focus in input
      this.hiddenRealInput.focus();
      let pos = this.hiddenRealInput.value.length;
      this.hiddenRealInput.setSelectionRange(pos, pos);
    }, 500);
  }

  runCommand() {
    this.addLine("");
    const commandAndParams = this.inputField.innerText;
    this.hiddenRealInput.value = "";
    var commandPieces = commandAndParams.split(" ");
    const command = commandPieces[0];
    commandPieces.shift();
    switch (command) {
      case "clear":
        this.clear();
        break;
      case "help":
        this.commands["help"].run(this, commandPieces);
        break;
      case "names":
        this.commands["names"].run(this, commandPieces);
        break;
      default:
        this.addLine(command + ": unknown command");
        break;
    }
    this.inputField.innerText = "";
  }

  addLine(newLine) {
    this.lines.push(newLine)
    if (this.lines.length > this.MAX_NUM_OF_LINES) {
      this.lines.shift();
    }
    this.output.innerHTML = "";
    for (let i = 0; i < this.lines.length; i++) {
      this.output.innerHTML += this.lines[i] + "<br >";
    }
  }

  clear() {
    this.lines = Array();
    this.output.innerHTML = "";
  }
}

// start console and listen for events

const virtualConsole = new Console();

document.getElementById("hiddenInput").addEventListener("input", function (event) {
  virtualConsole.inputField.innerText = event.target.value;
});

// start listening for keyboard inputs
document.addEventListener('keypress', function (event) {
  if (event.key == "Enter") {
    virtualConsole.runCommand();
  }
  // we go to bottom when any key is pressed
  document.getElementById("content").scrollTop = document.getElementById("content").scrollHeight;
});

class NamesApi {
  static async getNamesAlphabetically() {
    try {
      const response = await fetch("/api?page=2");
      const json = await response.json();
      return json;
    } catch (error) {
      console.log(error);
      return null;
    }
  }
}
