import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private static BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Manager");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 400);

        // Menu
        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(10));

        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button updateButton = new Button("Update Book");
        Button listButton = new Button("List Books");
        Button exitButton = new Button("Exit");

        menuBox.getChildren().addAll(addButton, removeButton, updateButton, listButton, exitButton);
        root.setLeft(menuBox);

        // Main content
        TextArea mainContent = new TextArea();
        mainContent.setEditable(false);
        root.setCenter(mainContent);

        // Event handlers
        addButton.setOnAction(e -> showAddBookDialog());
        removeButton.setOnAction(e -> showRemoveBookDialog());
        updateButton.setOnAction(e -> showUpdateBookDialog());
        listButton.setOnAction(e -> listBooks(mainContent));
        exitButton.setOnAction(e -> primaryStage.close());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        Button addButton = new Button("Add");

        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int year = Integer.parseInt(yearField.getText());

            Book book = new Book(title, author, isbn, year);
            bookManager.addBook(book);
            dialog.close();
        });

        dialogVBox.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Author:"), authorField,
                new Label("ISBN:"), isbnField,
                new Label("Year:"), yearField,
                addButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showRemoveBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Remove Book");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        Button removeButton = new Button("Remove");

        removeButton.setOnAction(e -> {
            String isbn = isbnField.getText();

            Book bookToRemove = null;
            for (Book book : bookManager.getBooks()) {
                if (book.getIsbn().equals(isbn)) {
                    bookToRemove = book;
                    break;
                }
            }

            if (bookToRemove != null) {
                bookManager.removeBook(bookToRemove);
                dialog.close();
            } else {
                showAlert("Book not found", "No book found with the provided ISBN.");
            }
        });

        dialogVBox.getChildren().addAll(new Label("ISBN:"), isbnField, removeButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showUpdateBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Update Book");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        TextField isbnField = new TextField();
        isbnField.setPromptText("Current ISBN");

        TextField newTitleField = new TextField();
        newTitleField.setPromptText("New Title");

        TextField newAuthorField = new TextField();
        newAuthorField.setPromptText("New Author");

        TextField newIsbnField = new TextField();
        newIsbnField.setPromptText("New ISBN");

        TextField newYearField = new TextField();
        newYearField.setPromptText("New Year");

        Button updateButton = new Button("Update");

        updateButton.setOnAction(e -> {
            String isbn = isbnField.getText();

            Book oldBook = null;
            for (Book book : bookManager.getBooks()) {
                if (book.getIsbn().equals(isbn)) {
                    oldBook = book;
                    break;
                }
            }

            if (oldBook != null) {
                String newTitle = newTitleField.getText();
                String newAuthor = newAuthorField.getText();
                String newIsbn = newIsbnField.getText();
                int newYear = Integer.parseInt(newYearField.getText());

                Book newBook = new Book(newTitle, newAuthor, newIsbn, newYear);
                bookManager.updateBook(oldBook, newBook);
                dialog.close();
            } else {
                showAlert("Book not found", "No book found with the provided ISBN.");
            }
        });

        dialogVBox.getChildren().addAll(
                new Label("Current ISBN:"), isbnField,
                new Label("New Title:"), newTitleField,
                new Label("New Author:"), newAuthorField,
                new Label("New ISBN:"), newIsbnField,
                new Label("New Year:"), newYearField,
                updateButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void listBooks(TextArea mainContent) {
        StringBuilder sb = new StringBuilder();
        for (Book book : bookManager.getBooks()) {
            sb.append(book.toString()).append("\n");
        }
        mainContent.setText(sb.toString());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
