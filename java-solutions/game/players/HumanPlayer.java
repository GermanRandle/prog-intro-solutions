package game.players;

import game.Action;
import game.ActionType;
import game.Move;
import game.Position;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner in;
    public HumanPlayer() {
        in = new Scanner(System.in);
    }

    @Override
    protected Action makeOrdinaryAction(Position position) {
        while (true) {
            // :NOTE: Зависимость вывода от Position
            System.out.println("Enter your move for " + position.getTurn());
            final String input = in.nextLine();

            if ("concede".equals(input)) {
                return new Action(ActionType.CONCEDE, position.getTurn());
            }
            if ("draw?".equals(input)) {
                final Action action = new Action(ActionType.TRY_DRAW, position.getTurn());
                if (position.isValid(action)) {
                    return action;
                }
                System.out.println("You can't suggest draw two times in a row");
                continue;
            }

            // :NOTE: Повтороное
            Scanner inLine = new Scanner(input);
            Move move;
            try {
                move = new Move(inLine.nextInt() - 1, inLine.nextInt() - 1, position.getTurn());
            } catch (final NoSuchElementException e) {
                System.out.println("Invalid input");
                continue;
            }


            if (inLine.hasNext()) {
                System.out.println("Invalid input (more than 2 tokens)");
                continue;
            }

            if (!position.isValid(move)) {
                System.out.println("Invalid cell");
                continue;
            }
            return move;
        }
    }

    @Override
    protected Action answerDraw(Position position) {
        System.out.println("Your opponent suggested draw. Do you accept/deny?");
        while (true) {
            String input = in.nextLine();
            switch (input) {
                case "accept":
                    return new Action(ActionType.ACCEPT_DRAW, position.getTurn());
                case "deny":
                    return new Action(ActionType.DENY_DRAW, position.getTurn());
                default:
                    System.out.println("Please enter \"accept\" or \"deny\"");
            }
        }
    }
}
