package tattool.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import net.sf.jasperreports.engine.util.SortedIntList;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.domain.model.User;
import tattool.domain.modelfx.CustomerFX;
import tattool.domain.modelfx.ServiceFX;
import tattool.domain.modelfx.SessionCashierFX;
import tattool.domain.modelfx.SessionFX;
import tattool.domain.modelfx.UserFX;

public class ConvertModelToFX {

	public static User convertUser(UserFX u) {
		User user = new User();
		if (u.getId() != null)
			user.setId(u.getId());
		user.setNome(u.getNome().get());
		String role = u.getRole().get();
		user.setRole(Integer.parseInt(role));
		user.setSenha(u.getSenha().get());
		user.setUsuario(u.getUsuario().get());
		return user;

	}

	public static UserFX convertUserFX(User u) {
		UserFX user = new UserFX(u.getUsuario(), u.getSenha(), u.getNome(), u.getRole().toString());
		user.setId(u.getId());
		return user;
	}

	public static List<UserFX> convertListUser(User[] findAllUsers) {
		List<UserFX> userFX = new ArrayList<>();
		List<User> user = Arrays.asList(findAllUsers);
		for (User u : user) {
			userFX.add(ConvertModelToFX.convertUserFX(u));
		}
		return userFX;
	}

	public static List<User> convertListUserFX(List<UserFX> user) {
		List<User> userConvertido = new ArrayList<>();
		for (UserFX u : user) {
			userConvertido.add(ConvertModelToFX.convertUser(u));
		}
		return userConvertido;
	}

	public static List<CustomerFX> convertListCustomer(Customer[] findAll) {
		List<Customer> customers = Arrays.asList(findAll);
		List<CustomerFX> clientes = new ArrayList<>();
		if (!customers.isEmpty()) {
			customers.forEach(c -> {
				CustomerFX conversao = new CustomerFX();
				conversao.setId(c.getId());
				conversao.setCpf(new SimpleStringProperty(c.getCpf()));
				conversao.setBirthDate(c.getBirthDate());
				conversao.setAddress(c.getAddress());
				conversao.setContact(c.getContact());
				if (c.getContact().getEmail().equals("") && !c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getPhone()));
				}
				if (!c.getContact().getEmail().equals("") && c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getEmail()));
				}
				if (!c.getContact().getEmail().equals("") && !c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getPhone()));
				}
				conversao.setName(new SimpleStringProperty(c.getName()));
				conversao.setRemoved(c.getRemoved());
				clientes.add(conversao);
			});
		}
		return clientes;
	}

	public static Customer convertCustomerFXtoCustomer(CustomerFX value) {
		Customer customer = new Customer();
		customer.setAddress(value.getAddress());
		customer.setContact(value.getContact());
		customer.setId(value.getId());
		customer.setName(value.getName().get());
		customer.setCpf(value.getCpf().get());
		customer.setBirthDate(value.getBirthDate());
		customer.setRemoved(value.getRemoved());

		return customer;
	}

	public static List<ServiceFX> convertListServicesToFx(Service[] findAll) {
		List<Service> services = Arrays.asList(findAll);
		List<ServiceFX> servicos = new ArrayList<>();
		services.forEach(s -> {
			ServiceFX service = new ServiceFX();
			service.setId(s.getId());
			service.setArts(s.getArts());
			service.setName(new SimpleStringProperty(s.getNameService()));
			service.setCustomeName(new SimpleStringProperty(s.getCustomer().getName()));
			service.setCustomer(s.getCustomer());
			service.setQuantSessions(s.getQuantSessions());
			service.setStatus(new SimpleStringProperty(s.getStatus()));
			service.setRemoved(s.getRemoved());
			servicos.add(service);
		});
		return servicos;
	}

	public static Service convertServiceFXtoService(ServiceFX value) {
		Service s = new Service();
		s.setId(value.getId());
		s.setCustomer(value.getCustomer());
		s.setArts(value.getArts());
		s.setNameService(value.getName().get());
		s.setQuantSessions(value.getQuantSessions());
		s.setRemoved(value.getRemoved());
		s.setStatus(value.getStatus().get());
		return s;
	}

	public static List<SessionFX> convertListSessionToSessionFX(List<Session> findByService) {
		List<SessionFX> sessionFX = new ArrayList<>();
		if(!findByService.isEmpty()){
			findByService.forEach(s -> {
				SessionFX sessao = new SessionFX();
				sessao.setId(s.getId());
				
				if (s.getDateSession() != null)
					sessao.setDate(new SimpleStringProperty(DateUtil.DateToString(s.getDateSession())));
				else
					sessao.setDate(new SimpleStringProperty("N�o agendado"));
				
				sessao.setDuration(new SimpleStringProperty(s.getDuration().toString()));
				sessao.setObs(s.getObs().toString());
				
				if (s.getPrice() != null)
					sessao.setPrice(new SimpleStringProperty(s.getPrice().toString()));
				else
					sessao.setPrice(new SimpleStringProperty("N�o acertado"));
				sessao.setRemoved(s.getRemoved());
				sessao.setService(s.getService());
				sessao.setStatus(new SimpleStringProperty(s.getStatus()));
			
				sessionFX.add(sessao);
			});
		}
		
		return sessionFX;
	}

	public static Session converSessionFXtoSession(SessionFX s) {
		Session sessao = new Session();
		if(s != null) {
			sessao.setId(s.getId());
			if(s.getDate().get().equals("N�o agendado"))
				sessao.setDateSession(null);
			else
			    sessao.setDateSession(DateUtil.StringToDate(s.getDate().get()));
			sessao.setDuration(Integer.parseInt(s.getDuration().get()));
			sessao.setObs(s.getObs());
			if(s.getPrice().get().equals("N�o acertado"))
				sessao.setPrice(null);
			else
				sessao.setPrice(new BigDecimal(s.getPrice().get()));
			sessao.setRemoved(s.getRemoved());
			sessao.setService(s.getService());
			sessao.setStatus(s.getStatus().get());
		}


		return sessao;
	}

	public static List<SessionCashierFX> convertSessinToSessionCashierFX(Session[] findAll) {
		List<Session> se = new ArrayList<>();
		List<SessionCashierFX> cashier = new ArrayList<>();
		if(findAll != null) {
			se = Arrays.asList(findAll);
			se.forEach(s -> {
				SessionCashierFX cash = new SessionCashierFX();
				
				if (s.getDateSession() != null)
					cash.setDate(new SimpleStringProperty(DateUtil.DateToString(s.getDateSession())));
				else
					cash.setDate(new SimpleStringProperty("N�o agendado"));
				
				cash.setNomeServico(new SimpleStringProperty(s.getService().getNameService()));
				cash.setDuration(s.getDuration());
				
				if (!s.getObs().equals(""))
					cash.setObs(s.getObs().toString());
				else
					cash.setObs("");
				
				if (s.getPrice() != null)
					cash.setPreco(new SimpleStringProperty(s.getPrice().toString()));
				else
					cash.setPreco(new SimpleStringProperty("N�o acertado"));
				if (s.getPaid() != null)
					cash.setPago(new SimpleStringProperty(s.getPaid().toString()));
				else
					cash.setPago(new SimpleStringProperty("N�o pago"));
				cash.setRemoved(s.getRemoved());
				cash.setService(s.getService());
				cash.setStatus(s.getStatus());
				cash.setPaid(s.getPaid());
				cash.setPrice(s.getPrice());
				cash.setId(s.getId());
				cash.setDateSession(s.getDateSession());
				cashier.add(cash);
			});
		}
		
		return cashier;
	}

	public static Session convertSessionCashierFXToSession(SessionCashierFX cashierSession) {
		Session session = new Session();
		session.setId(cashierSession.getId());
		session.setDateSession(cashierSession.getDateSession());
		session.setDuration(cashierSession.getDuration());
		session.setObs(cashierSession.getObs());
		session.setPaid(cashierSession.getPaid());
		session.setPrice(cashierSession.getPrice());
		session.setRemoved(cashierSession.getRemoved());
		session.setService(cashierSession.getService());
		session.setStatus(cashierSession.getStatus());
		return session;
	}

}
