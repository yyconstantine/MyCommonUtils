@AllArgsConstructor
public class TreeUtil {

    private List<TreeVO> remainList;
    
    public List<TreeVO> buildTree(List<TreeVO> userNodeList) {
        List<TreeVO> rootList = findRoots();
        List<TreeVO> tmpList = findChildren(findChildren(userNodeList, remainList), rootList);
        List<TreeVO> treeList = new LinkedList<>();
        tmpList.forEach(node -> {
            if (node.getPId().equals("0"))
                treeList.add(node);
        });
        return treeList;
    }

    private List<TreeVO> findRoots() {
        List<TreeVO> tmpList = new ArrayList<>();
        List<TreeVO> rootList = new ArrayList<>();
        remainList.forEach(node -> {
            if (node.getPId().equals("0")) {
                tmpList.add(node);
                rootList.add(node);
            }
        });
        remainList.removeAll(tmpList);
        return rootList;
    }

    private List<TreeVO> findChildren(List<TreeVO> inComeList, List<TreeVO> list) {
        List<TreeVO> tmpList = new ArrayList<>();
        list.forEach(remain -> {
            List<TreeVO> children = new ArrayList<>();
            inComeList.forEach(income -> {
                if (income.getPId().equals(remain.getId())) {
                    tmpList.add(remain);
                    children.add(income);
                    remain.setChildren(children);
                }
            });
        });
        inComeList.addAll(tmpList);
        remainList.removeAll(tmpList);
        return inComeList;
    }

}