package jp.ndca.handlersocket;

import java.util.ArrayList;
import java.util.List;

/**
 * HandlerSocketのコマンド実行結果を表現するクラスです
 * @author moaikids
 *
 */
public class HandlerSocketResult {
        private int status;//ステータスコード
        private int fieldNum;//返却されたメッセージの数
        private List<String> messages;//メッセージ

        public HandlerSocketResult(){
                super();
                messages = new ArrayList<String>();
        }
       
        public HandlerSocketResult(int status, int fieldNum, List<String> messages){
                this();
                this.status = status;
                this.fieldNum = fieldNum;
                this.messages = messages;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        public int getFieldNum() {
                return fieldNum;
        }

        public void setFieldNum(int fieldNum) {
                this.fieldNum = fieldNum;
        }

        public List<String> getMessages() {
                return messages;
        }

        public void setMessages(List<String> messages) {
                this.messages = messages;
        }
       
        @Override
        public String toString(){
                StringBuilder builder = new StringBuilder();
                builder.append(status).append("\t");
                builder.append(fieldNum).append("\t");
                builder.append(messages.size()).append("\n");
                for(String message : messages){
                        builder.append(message).append("\n");
                }
                return builder.toString();
        }
}

