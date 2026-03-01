package game;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import game.GameBean;
import game.GameBean.GamePlayer;

public class GameServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GameBean game = (GameBean) request.getSession(true).getAttribute("gameBean");
        int line = Integer.parseInt(request.getParameter("Line"));
        int col = Integer.parseInt(request.getParameter("Col"));
        game.playPlayerTurn(line, col);
        
        GamePlayer winner = game.getWinner();
        switch (winner) {
            case NOBODY:
                if (game.hasEmptyCell()) {
                    game.playComputerTurn();
                    switch (game.getWinner()) {
                        case NOBODY:
                            break;
                        case COMPUTER:
                            request.setAttribute("la computadora ha", "ganado");
                            break;
                        case USER:
                            request.setAttribute("tu has", "ganado");
                            break;
                    }
                }
                break;
            case COMPUTER:
                request.setAttribute("winner", "la computadora ha");
                break;
            case USER:
                request.setAttribute("winner", "tu has");
                break;
        }
        
        if (winner == GamePlayer.NOBODY && !game.hasEmptyCell()) {
            request.setAttribute("nadie ha", "ganado");
        }
        
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}