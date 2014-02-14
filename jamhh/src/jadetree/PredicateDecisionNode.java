package jadetree;

import jutlis.algebra.Predicate;

/**
 *
 * @author kommusoft
 * @param <TSource>
 */
public class PredicateDecisionNode<TSource> extends DecisionInode<TSource> {

    final Predicate<? super TSource> predicate;
    DecisionNode<TSource> trueNode;
    DecisionNode<TSource> falseNode;

    public PredicateDecisionNode(final DecisionTree<TSource> tree, Predicate<? super TSource> predicate, DecisionNode<TSource> trueNode, DecisionNode<TSource> falseNode) {
        super(tree);
        this.predicate = predicate;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    public PredicateDecisionNode(final DecisionTree<TSource> tree, Predicate<? super TSource> predicate, DecisionNode<TSource> trueNode, DecisionNode<TSource> falseNode, Iterable<TSource> toInsert) {
        this(tree, predicate, trueNode, falseNode);
        for (TSource source : toInsert) {
            this.insert(source);
        }
    }

    public PredicateDecisionNode(final DecisionTree<TSource> tree, Predicate<? super TSource> predicate) {
        this(tree, predicate, new DecisionLeaf<TSource>(tree), new DecisionLeaf<TSource>(tree));
    }

    public PredicateDecisionNode(final DecisionTree<TSource> tree, Predicate<? super TSource> predicate, Iterable<TSource> toInsert) {
        this(tree, predicate);
        for (TSource source : toInsert) {
            this.insert(source);
        }
    }

    @Override
    public DecisionNode<TSource> nextHop(TSource source) {
        if (this.getPredicate().evaluate(source)) {
            return this.getTrueNode();
        } else {
            return this.getFalseNode();
        }
    }

    @Override
    public void makeDirty() {
        this.getTrueNode().makeDirty();
        this.getFalseNode().makeDirty();
        super.makeDirty();
    }

    @Override
    protected DecisionLeaf<TSource> recalcMaximumLeaf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the predicate
     */
    public Predicate<? super TSource> getPredicate() {
        return predicate;
    }

    /**
     * @return the trueNode
     */
    public DecisionNode<TSource> getTrueNode() {
        return trueNode;
    }

    /**
     * @param trueNode the trueNode to set
     */
    public void setTrueNode(DecisionNode<TSource> trueNode) {
        this.trueNode = trueNode;
    }

    /**
     * @return the falseNode
     */
    public DecisionNode<TSource> getFalseNode() {
        return falseNode;
    }

    /**
     * @param falseNode the falseNode to set
     */
    public void setFalseNode(DecisionNode<TSource> falseNode) {
        this.falseNode = falseNode;
    }

}