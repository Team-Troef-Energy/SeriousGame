class PythonService{
    fetchMessage = async (message: { [key: string]: any }) => {
        const aiBackendUrl = {
            "localhost": 'http://localhost:5000',
            "frontend.dev.troefgame.jonaqhan.nl": "https://ai-backend.dev.troefgame.jonaqhan.nl",
            "troefgame.duckdns.org": "http://troefgame.duckdns.org:5002",
        }[window.location.hostname] || null; // Hostname does not include port

        try {
          const response = await fetch(`${aiBackendUrl}/python-data`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(message)
          });
      
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
      
          const data = await response.json();
          return data;
        } catch (error) {
          console.error("There was a problem with the fetch operation:", error);
          throw error;
        }
      }
}

export const pythonService = new PythonService();