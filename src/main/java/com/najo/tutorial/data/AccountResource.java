package com.najo.tutorial.data;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

class AccountResource extends ResourceSupport {

	private final Account account;

	public AccountResource(Account account) {
		this.account = account;
		String username = this.account.getUsername();
		this.add(linkTo(methodOn(AccountRestController.class).readAccount(username)).withSelfRel());
		this.add(linkTo(methodOn(AccountRestController.class).readAccounts()).withRel("allAccounts"));

	}

	public Account getAccount() {
		return account;
	}
}