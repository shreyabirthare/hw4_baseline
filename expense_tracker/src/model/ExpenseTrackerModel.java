package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
  /**
   * expense tracker model
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<ExpenseTrackerModelListener>(); // Initializing the listeners list
  }
/**
 * add transaction function
 * @param t
 */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    stateChanged();
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }
/**
 * remove transaction
 * @param t
 */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    stateChanged();
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }
/**
 * get transaction
 * @return
 */
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }
/**
 * set matched filter indices
 * @param newMatchedFilterIndices
 */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      stateChanged();
  }
/**
 * get matched filter indices
 * @return
 */
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      if (listener != null && !listeners.contains(listener)) {
        listeners.add(listener);
        return true;
      }
      return false;
  }

  public boolean unregister(ExpenseTrackerModelListener listener) {
    if (listener != null && listeners.contains(listener)) {
      listeners.remove(listener);
      return true;

    }
    return false;
  }
/**
 * number of listeners
 * @return
 */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      //TODO
      return listeners.size();
  }
/**
 * contains listener
 * @param listener
 * @return
 */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
      return listeners.contains(listener);
  }

  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
      for (ExpenseTrackerModelListener listener : listeners) {
        listener.update(this);
      }
  }
}
