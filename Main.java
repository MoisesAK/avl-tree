import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        class Node {
            int value;
            int height;
            Node left;
            Node right;

            public Node(int value) {
                this.value = value;
                this.height = 1;
            }
        }

        class Tree {
            private Node root;

            public Tree() {
                root = null;
            }
            
            public Node insert(Node node, int value) {
                if (node == null) {
                    return new Node(value);
                }

                if(value == node.value) {
                    System.out.println("NÃO PODE INSERIR VALORES QUE JÁ EXISTEM NA ÁRVORE");
                    return node;
                }

                if (value < node.value) {
                    node.left = insert(node.left, value);
                }

                if (value > node.value) {
                    node.right = insert(node.right, value);
                }

                updateHeight(node);

                Node nodeBalanced = doBalanceIfNecessary(node, value);
                if (nodeBalanced != null) return nodeBalanced;

                return node;
            }

            private boolean search(Node node, int value) {
                if (node == null) {
                    return false;
                }

                if (value < node.value) {
                    return search(node.left, value);
                } else if (value > node.value) {
                    return search(node.right, value);
                } else {
                    return true;
                }
            }

            private Node delete(Node node, int value) {
                if (node == null) {
                    return node;
                }

                if (value < node.value) {
                    node.left = delete(node.left, value);
                } else if (value > node.value) {
                    node.right = delete(node.right, value);
                } else {
                    node = foundDeleteValue(node);
                }

                if (node == null) {
                    return node;
                }

                updateHeight(node);

                Node nodeBalanced = doBalanceIfNecessary(node, value);
                if (nodeBalanced != null) return nodeBalanced;

                return node;
            }

            private Node foundDeleteValue(Node node) {
                if(withoutChildren(node)){
                    node = null;
                }else if (withUnlessOneChildNonNull(node)) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    Node minNode = getMinNode(node.right);

                    node.value = minNode.value;

                    node.right = delete(node.right, minNode.value);
                }
                return node;
            }

            private static boolean withUnlessOneChildNonNull(Node node) {
                return node != null && (
                        (node.left == null && node.right != null) ||
                        (node.left != null && node.right == null)
                );
            }

            private static boolean withoutChildren(Node node) {
                return node.left == null && node.right == null;
            }

            private Node getMinNode(Node node) {
                while (node.left != null) {
                    node = node.left;
                }
                return node;
            }

            private int getHeight(Node node) {
                if (node == null)
                    return 0;
                return node.height;
            }

            private Node doBalanceIfNecessary(Node node, int value) {
                int balance = getBalance(node);

                if (balance > 1 && value < node.left.value) {
                    return rightRotation(node);
                }

                if (balance < -1 && value > node.right.value) {
                    return leftRotation(node);
                }

                if (balance > 1 && value > node.left.value) {
                    node.left = leftRotation(node.left);
                    return rightRotation(node);
                }

                if (balance < -1 && value < node.right.value) {
                    node.right = rightRotation(node.right);
                    return leftRotation(node);
                }
                return null;
            }

            private int getBalance(Node node) {
                if (node == null)
                    return 0;
                return getHeight(node.left) - getHeight(node.right);
            }

            private void updateHeight(Node node) {
                if (node != null) {
                    node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
                }
            }

            private Node rightRotation(Node node) {
                //holding values
                Node nodeLeft = node.left;
                Node aux = nodeLeft.right;

                //swapping
                nodeLeft.right = node;
                node.left = aux;

                //update hights
                updateHeight(node);
                updateHeight(nodeLeft);

                return nodeLeft;
            }

            private Node leftRotation(Node node) {
                //holding values
                Node nodeRight = node.right;
                Node aux = nodeRight.left;

                //swapping
                nodeRight.left = node;
                node.right = aux;

                //update hights
                updateHeight(node);
                updateHeight(nodeRight);

                return nodeRight;
            }

            public void delete(int value) {
                root = delete(root, value);
            }
            public void insert(int value) {
                root = insert(root, value);
            }
            public boolean search(int value) {
                return search(root, value);
            }
        }

        Tree tree = new Tree();
        Scanner scanner = new Scanner(System.in);

        int option = 0;
        while (option != 4){
            System.out.println("Selecione uma opção:");
            System.out.println("1 - INSERIR");
            System.out.println("2 - ENCONTRAR");
            System.out.println("3 - DELETAR");
            System.out.println("4 - SAIR");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Digite o valor a ser INSERIDO: ");
                    int valueToInsert = scanner.nextInt();
                    tree.insert(valueToInsert);
                    break;
                case 2:
                    System.out.print("Digite o valor a ser ENCONTRADO: ");
                    int valueToSearch = scanner.nextInt();
                    if (tree.search(valueToSearch)) {
                        System.out.println("VALOR ENCONTRADO.");
                    } else {
                        System.out.println("VALOR NÃO ENCONTRADO.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o valor a ser DELETADO: ");
                    int valueToDelete = scanner.nextInt();
                    tree.delete(valueToDelete);
                    break;
                case 4:
                    System.out.println("SAINDO...");
                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA. TENTE NOVAMENTE");
            }
        }

        scanner.close();
    }
}
