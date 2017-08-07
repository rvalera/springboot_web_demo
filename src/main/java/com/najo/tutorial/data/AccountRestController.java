package com.najo.tutorial.data;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
class AccountRestController {

	private final AccountRepository accountRepository;

	@Autowired
	AccountRestController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@RequestMapping(path = "/account", method = RequestMethod.GET)
	HttpEntity<List<AccountResource>> readAccounts() {
		List<AccountResource> accountResourceList = accountRepository.findAll().stream().map(AccountResource::new)
				.collect(Collectors.toList());
		return new HttpEntity<List<AccountResource>>(accountResourceList);  
	}

	@RequestMapping(path = "/account/{userId}", method = RequestMethod.GET)
	AccountResource readAccount(@PathVariable String userId) {
		return new AccountResource(
				this.accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId)));
	}

	@RequestMapping(path = "/account", method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Account account) {
		Account savedAccount = this.accountRepository.save(account);

		Link forOneAccount = new AccountResource(savedAccount).getLink("self");
		return ResponseEntity.created(URI.create(forOneAccount.getHref())).build();

		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(savedAccount.getUsername())
		// .toUri();
		// return ResponseEntity.created(location).build();
	}

}