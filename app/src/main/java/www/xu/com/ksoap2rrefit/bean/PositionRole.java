package www.xu.com.ksoap2rrefit.bean;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.List;

/**
 * 岗位选择列表
 */
@Element(name = "ns2:queryPostModelResponse")
public class PositionRole {
    @Attribute(name = "xmlns:ns2")
    String nameSpace;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Element(name = "return")
    private List<Pos> result;

    public List<Pos> getResult() {
        return result;
    }

    public void setResult(List<Pos> result) {
        this.result = result;
    }

    public static class Pos {
        String postCode;
        String postId;
        String postName;
        String postRemark;
        boolean selected;
        List ownRoleList;
        List ownUserList;

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getPostRemark() {
            return postRemark;
        }

        public void setPostRemark(String postRemark) {
            this.postRemark = postRemark;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public List getOwnRoleList() {
            return ownRoleList;
        }

        public void setOwnRoleList(List ownRoleList) {
            this.ownRoleList = ownRoleList;
        }

        public List getOwnUserList() {
            return ownUserList;
        }

        public void setOwnUserList(List ownUserList) {
            this.ownUserList = ownUserList;
        }
    }

}
