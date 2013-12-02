package controllers;

import br.ufrn.lii.brcollector.callback.AbstractRealtimeProcessCallback;
import br.ufrn.lii.brcollector.connection.CollectorConnectionProvider;
import br.ufrn.lii.brcollector.connection.exception.AlreadyMappedException;
import br.ufrn.lii.brcollector.connection.rmi.Request;
import br.ufrn.lii.brcollector.connection.rmi.RequestRDA;
import br.ufrn.lii.commonsdomain.ProcessVariableSubscription;
import br.ufrn.lii.commonsdomain.TagItemGroup;
import br.ufrn.lii.commonsdomain.exception.UnregisteredGroupException;
import br.ufrn.lii.commonsdomain.process.ProcessData;
import br.ufrn.lii.commonsdomain.process.TagItem;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Teles
 */
public class TestRDA {

    private Request request;


    public TestRDA() {
        request = null;
        try {
            request = CollectorConnectionProvider.connectToCollector(BRCollectorTestConstants.HOST,
                    BRCollectorTestConstants.PORT,
                    BRCollectorTestConstants.SERVICE_NAME);
        } catch (NotBoundException ex) {
            TestExceptionManager.log("Errou ao conectar-se ao coletor.", ex);
        } catch (MalformedURLException ex) {
            TestExceptionManager.log("Errou ao conectar-se ao coletor.", ex);
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou ao conectar-se ao coletor.", ex);
        }
    }

    public void startTests() {
        List<String> listServerRDA = listServerRDA();

        System.out.println(listServerRDA.size() + " servidores encontrados");
 
        for (String serverName : listServerRDA) {
            
            System.out.println("Testando o servidor " + serverName);

            RequestRDA requestRDA = connectServerRDA(serverName);
            System.out.println("Conectado ao servidor: " + serverName);

            List<TagItem> tagItems = searchTags(requestRDA);
            System.out.println("Tags listadas");

            TagItemGroup tagItemGroup = registerGroup(requestRDA);
            System.out.println("Grupo registrado");

            addItemsToGroup(requestRDA, tagItemGroup, tagItems);
            System.out.println("TagsAdicionadas ao grupo");

            getCurrentValues(requestRDA, tagItemGroup);
//            System.out.println("Current Values obtidos FIM");

//            removeTagItemsFromGroup(requestRDA, tagItemGroup, tagItems);
//            System.out.println("Tags removidas do grupo");
//
//            getCurrentValues(requestRDA, tagItemGroup);
//            System.out.println("Current values apos remoção das tags.");
//
            createSubscription(requestRDA, tagItems);
            System.out.println("Subscription criada");

//            for (int i = 0; i < 10; i++) {
//                System.out.println("-------------------------------");
//            }
        }
    }

    private List<String> listServerRDA() {
        try {
            return request.listServersRDA();
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou ao lista servidores RDA.", ex);
        }

        return null;
    }

    private RequestRDA connectServerRDA(String serverName) {
        try {
            return request.connectServerRDA(serverName);
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou ao conectar ao servidor " + serverName, ex);
        }

        return null;
    }

    public static void main(String[] args) {
        TestRDA testRDA = new TestRDA();
        testRDA.startTests();
    }

    private List<TagItem> searchTags(RequestRDA requestRDA) {
        try {
            List<TagItem> searchTagItems = requestRDA.searchTagItems("*");
            System.out.println("Tags listadas:");
            for (TagItem tagItem : searchTagItems) {
                System.out.println(tagItem);
            }
            return searchTagItems;
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou no searchTags RDA.", ex);
        }

        return null;
    }

    private TagItemGroup registerGroup(RequestRDA requestRDA) {
        try {
            return requestRDA.createGroup("Grupo tags de teste");
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou no registro do TagItemGroup RDA.", ex);
        } catch (AlreadyMappedException ex) {
            TestExceptionManager.log("Errou no registro do TagItemGroup RDA.", new Exception(ex));
        }
        return null;
    }

    private void addItemsToGroup(RequestRDA requestRDA, TagItemGroup tagItemGroup, List<TagItem> tagItems) {
        try {
            requestRDA.addItemsToGroup(tagItemGroup, tagItems.toArray(new TagItem[tagItems.size()]));
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou ao adicionar tags ao TagItemGroup RDA.", new Exception(ex));
        }
    }

    private Map<TagItem, ProcessData> getCurrentValues(RequestRDA requestRDA, TagItemGroup tagItemGroup) {
        try {
            Map<TagItem, ProcessData> currentValues = requestRDA.getCurrentValues(tagItemGroup);

            Set<TagItem> keySet = currentValues.keySet();
            System.out.println("Current Values Obtidos:");
            for (TagItem key : keySet) {
                System.out.println(key + " | " + currentValues.get(key).getValue().getStringValue());
            }

            return currentValues;
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou no getCurrentValues RDA.", new Exception(ex));
        } catch (UnregisteredGroupException ex) {
            TestExceptionManager.log("Errou no getCurrentValues RDA.", new Exception(ex));
        }
        return null;
    }

    private void createSubscription(RequestRDA requestRDA, List<TagItem> tagItems) {
        try {
            ProcessVariableSubscription processVariableSubscription = requestRDA.createSubscription("Subscription teste RDA", true, 1000, 0.1f, new MyRealtimeProcessCallback());

            requestRDA.addItemsToSubscription(processVariableSubscription, tagItems.toArray(new TagItem[tagItems.size()]));
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou no create subscription RDA", ex);
        } catch (AlreadyMappedException ex) {
            TestExceptionManager.log("Errou no create subscription RDA", new Exception(ex));
        }
    }

    private void removeTagItemsFromGroup(RequestRDA requestRDA, TagItemGroup tagItemGroup, List<TagItem> tagItems) {
        try {
            requestRDA.removeItemsFromGroup(tagItemGroup, tagItems.toArray(new TagItem[tagItems.size()]));
        } catch (RemoteException ex) {
            TestExceptionManager.log("Errou ao remover tags do group RDA", new Exception(ex));
        }
    }
}

