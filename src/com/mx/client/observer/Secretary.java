package com.mx.client.observer;



import java.util.ArrayList;
import java.util.List;


/**
 * 具体主题（或可观察者）角色:前台<br>
 * 这里省略了抽象主题(或可观察者Subject)角色<br>
 * @author Administrator
 */
public class Secretary {

  /**所有观察者*/
  private List<Observer> observers = new ArrayList<Observer>();
  /**可观察者的动作*/
  private String action;

  /**
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * 添加一个观察者<br>
   * @param observer
   */
  public void add(Observer observer) {
    observers.add(observer);
    System.out.println("添加成功木有"+observers.size());
  }

  /**
   * 删除一个观察者<br>
   * @param observer
   */
  public void remove(Observer observer) {
    observers.remove(observer);
  }

  /**
   * 通知所有观察者<br>
   * @param action
   */
  public void notify_(String action) {
    setAction(action);
    System.out.println("执行前奏---"+action);
    System.out.println("老子无语了"+observers.size());
    for (Observer observer : observers) {
    	 observer.getClass();
         observer.update();
    }
    
    System.out.println("循环执行完毕");
  }

}
