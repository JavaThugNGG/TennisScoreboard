<%
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPages = (Integer) request.getAttribute("totalPages");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href=${pageContext.request.contextPath}>Home</a>
                <a class="nav-link" href="#">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <input class="input-filter" placeholder="Filter by name" type="text" />
            <div>
                <a href="#">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <tr>
                <td>${firstPlayerName1}</td>
                <td>${secondPlayerName1}</td>
                <td><span class="winner-name-td">${winnerName1}</span></td>
            </tr>
            <tr>
                <td>${firstPlayerName2}</td>
                <td>${secondPlayerName2}</td>
                <td><span class="winner-name-td">${winnerName2}</span></td>
            </tr>
            <tr>
                <td>${firstPlayerName3}</td>
                <td>${secondPlayerName3}</td>
                <td><span class="winner-name-td">${winnerName3}</span></td>
            </tr>
            <tr>
                <td>${firstPlayerName4}</td>
                <td>${secondPlayerName4}</td>
                <td><span class="winner-name-td">${winnerName4}</span></td>
            </tr>
            <tr>
                <td>${firstPlayerName5}</td>
                <td>${secondPlayerName5}</td>
                <td><span class="winner-name-td">${winnerName5}</span></td>
            </tr>
        </table>

        <div class="pagination">

            <form method="get" action="#" style="display:inline;">
                <input type="hidden" name="page" value="<%= (currentPage > 1) ? (currentPage - 1) : 1 %>" />
                <button type="submit" class="prev" <%= (currentPage == 1) ? "disabled" : "" %>> &lt; </button>
            </form>

            <% for (int i = 1; i <= totalPages; i++) { %>
            <form method="get" action="#" style="display:inline;">
                <input type="hidden" name="page" value="<%= i %>" />
                <button type="submit" class="num-page" <%= (i == currentPage) ? "disabled" : "" %>><%= i %></button>
            </form>
            <% } %>

            <form method="get" action="#" style="display:inline;">
                <input type="hidden" name="page" value="<%= (currentPage < totalPages) ? (currentPage + 1) : totalPages %>" />
                <button type="submit" class="next" <%= (currentPage == totalPages) ? "disabled" : "" %>> &gt; </button>
            </form>

        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
