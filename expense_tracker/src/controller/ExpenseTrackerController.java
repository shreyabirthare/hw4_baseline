package controller;

import view.ExpenseTrackerView;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ExpenseTrackerController implements model.ExpenseTrackerModelListener {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.model.register(this);
  }

  public void setFilter(TransactionFilter filter) {
    this.filter = filter;
  }
/**
 * add transaction
 * @param amount
 * @param category
 * @return
 */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    return true;
  }
/** 
 * apply filter
 */
  public void applyFilter() {
    //null check for filter
    if(filter!=null){
      List<Transaction> transactions = model.getTransactions();
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      model.setMatchedFilterIndices(rowIndexes);
    } else {
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();
    }
  }
/**
 * undo transaction
 * @param rowIndex
 * @return
 */
  //for undoing any selected transaction
  public boolean undoTransaction(int rowIndex) {
    if (rowIndex >= 0 && rowIndex < model.getTransactions().size()) {
      Transaction removedTransaction = model.getTransactions().get(rowIndex);
      model.removeTransaction(removedTransaction);
      // The undo was allowed.
      return true;
    }

    // The undo was disallowed.
    return false;
  }    

  // Implement the update method from ExpenseTrackerModelListener
  @Override
  public void update(ExpenseTrackerModel model) {
    view.update(model);
  }
}
