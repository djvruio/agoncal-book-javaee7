package org.agoncal.book.javaee7.chapter12;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 7 with Glassfish 4
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
@Named
@RequestScoped
public class BookController {

  // ======================================
  // =             Attributes             =
  // ======================================

  @Inject
  private BookEJB bookEJB;

  private Book book = new Book();
  private String tags;

  // ======================================
  // =           Public Methods           =
  // ======================================

  public String doCreateBook() {
    book.setTags(transformToList(tags));
    bookEJB.createBook(book);
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Book created",
            "The book" + book.getTitle() + " has been created with id=" + book.getId()));
    return "newBook.xhtml";
  }

  public void doFindBookById(){
    book = bookEJB.findBook(book.getId());
  }

  // ======================================
  // =           Private methods          =
  // ======================================

  private List<String> transformToList(String tagsRequestParameter) {
    if (tagsRequestParameter == null)
      return null;
    List<String> listOfTags = new ArrayList<>();
    StringTokenizer tokens = new StringTokenizer(tagsRequestParameter, ",");
    while (tokens.hasMoreElements()) {
      listOfTags.add(((String) tokens.nextElement()).trim());
    }
    return listOfTags;
  }

  // ======================================
  // =          Getters & Setters         =
  // ======================================


  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public Language[] getLanguages() {
    return Language.values();
  }
}