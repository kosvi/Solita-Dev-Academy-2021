async function listNames() {
	const content = document.getElementById("jsContent");
	content.innerHTML = "";
	try {
		const response = await fetch("/api?page=2");
		const responseJson = await response.json();
		for (let i = 0; i < responseJson.names.length; i++) {
			content.innerHTML += responseJson.names[i] + "<br />";
		}
	} catch (error) {
		console.log(error);
	}
}
