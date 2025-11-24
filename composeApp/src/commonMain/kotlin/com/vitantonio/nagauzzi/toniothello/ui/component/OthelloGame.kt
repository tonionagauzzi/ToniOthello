import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.toniothello.ui.component.CellState
import com.vitantonio.nagauzzi.toniothello.ui.component.Language
import com.vitantonio.nagauzzi.toniothello.ui.component.Player

@Composable
fun OthelloGame(language: Language) {
    // Temporary state (will be replaced with actual game logic)
    var board by remember {
        mutableStateOf(
            Array(8) { Array(8) { CellState.EMPTY } }.apply {
                // Initial Othello setup
                this[3][3] = CellState.WHITE
                this[3][4] = CellState.BLACK
                this[4][3] = CellState.BLACK
                this[4][4] = CellState.WHITE
            }
        )
    }
    var currentPlayer by remember { mutableStateOf(Player.BLACK) }
    var blackScore by remember { mutableStateOf(2) }
    var whiteScore by remember { mutableStateOf(2) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Game status
        GameStatus(
            currentPlayer = currentPlayer,
            blackScore = blackScore,
            whiteScore = whiteScore,
            language = language
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Game board
        OthelloBoard(
            board = board,
            onCellClick = { row, col ->
                // Placeholder click handler (logic will be implemented later)
                if (board[row][col] == CellState.EMPTY) {
                    val newBoard = board.map { it.clone() }.toTypedArray()
                    newBoard[row][col] =
                        if (currentPlayer == Player.BLACK) CellState.BLACK else CellState.WHITE
                    board = newBoard
                    currentPlayer =
                        if (currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reset button
        Button(
            onClick = {
                board = Array(8) { Array(8) { CellState.EMPTY } }.apply {
                    this[3][3] = CellState.WHITE
                    this[3][4] = CellState.BLACK
                    this[4][3] = CellState.BLACK
                    this[4][4] = CellState.WHITE
                }
                currentPlayer = Player.BLACK
                blackScore = 2
                whiteScore = 2
            }
        ) {
            Text(if (language == Language.JAPANESE) "もういちど" else "New Game")
        }
    }
}
