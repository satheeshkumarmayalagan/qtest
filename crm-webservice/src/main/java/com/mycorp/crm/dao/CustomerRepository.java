package com.mycorp.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mycorp.crm.schema.types.Address;
import com.mycorp.crm.schema.types.AddressType;
import com.mycorp.crm.schema.types.Customer;
import com.mycorp.crm.utils.DateConverter;

/**
 * CustomerRepository is used to store and access the customer in the database
 * 
 */
@Repository
public class CustomerRepository {

	private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Find customer.
	 *
	 * @param customerId
	 *            the customer id
	 * @return the customer
	 */
	public Customer findCustomer(Integer customerId) {
		Assert.notNull(customerId, "The customer id must not be null");
		log.info("FindCustomer {}", customerId);
		List<Customer> customers = jdbcTemplate.query("select * from customer where customer_id = ?",
				new Object[] { customerId }, new CustomerRowMapper());

		Customer customer = customers != null && !customers.isEmpty() ? customers.get(0) : null;
		if (customer != null) {
			List<Address> addresses = getAddress(customerId);
			if (addresses != null && !addresses.isEmpty()) {
				customer.getAddress().addAll(addresses);
			}
		}
		return customer;
	}

	/**
	 * Creates the customer.
	 *
	 * @param customer
	 *            the customer
	 * @return the int
	 */
	public int createCustomer(Customer customer) {
		Assert.notNull(customer, "The customer must not be null");
		log.info("CreateCustomer {}", customer);
		final String INSERT_SQL = "insert into customer (first_name, last_name, date_of_birth, email_address) values(?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "customer_id" });
				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getLastName());
				ps.setDate(3, DateConverter.toSqlDate(customer.getDateOfBirth()));
				ps.setString(4, customer.getEmailAddress());
				return ps;
			}
		}, keyHolder);
		int customerId = keyHolder.getKey().intValue();
		createAddress(customerId, customer.getAddress());
		return customerId;
	}

	/**
	 * Update customer.
	 *
	 * @param customer
	 *            the customer
	 * @return true, if successful
	 */
	public boolean updateCustomer(Customer customer) {
		Assert.notNull(customer, "The customer must not be null");
		log.info("UpdateCustomer {}", customer);
		final String UPDATE_SQL = "update customer set first_name = ? , last_name = ?, date_of_birth= ?, email_address = ? where customer_id = ?";
		int cnt = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getLastName());
				ps.setDate(3, DateConverter.toSqlDate(customer.getDateOfBirth()));
				ps.setString(4, customer.getEmailAddress());
				ps.setInt(5, customer.getCustomerId());
				return ps;
			}
		});
		deleteAddress(customer.getCustomerId());
		createAddress(customer.getCustomerId(), customer.getAddress());
		return cnt > 0;
	}

	/**
	 * Creates the.
	 *
	 * @param customerId
	 *            the customer id
	 * @param addresses
	 *            the addresses
	 * @return true, if successful
	 */
	public boolean createAddress(Integer customerId, List<Address> addresses) {
		log.info("CreateCustomerAddress {}", customerId);
		final String INSERT_SQL = "insert into address (post_code,state, street_name, street_number, unit_number, suburb, address_type, customer_id) values (?, ?, ?, ?, ?,?, ?, ?)";
		if (addresses != null && !addresses.isEmpty()) {
			int[] updateCounts = jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, addresses.get(i).getPostCode());
					ps.setString(2, addresses.get(i).getState());
					ps.setString(3, addresses.get(i).getStreetName());
					ps.setString(4, addresses.get(i).getStreetNumber());
					ps.setString(5, addresses.get(i).getUnitNumber());
					ps.setString(6, addresses.get(i).getSuburb());
					ps.setString(7, addresses.get(i).getAddressType() != null
							? addresses.get(i).getAddressType().value() : null);
					ps.setInt(8, customerId);
				}

				@Override
				public int getBatchSize() {
					return addresses.size();
				}
			});
			return updateCounts.length == addresses.size();
		} else
			return false;
	}

	/**
	 * Delete customer.
	 *
	 * @param customerId
	 *            the customer id
	 * @return true, if successful
	 */
	public boolean deleteCustomer(Integer customerId) {
		Assert.notNull(customerId, "The customer must not be null");
		log.info("DeleteCustomer{}", customerId);
		deleteAddress(customerId);
		final String DELETE_SQL = "delete from customer where customer_id = ?";

		int cnt = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
				ps.setInt(1, customerId);
				return ps;
			}
		});
		return cnt > 0;
	}

	/**
	 * Delete address.
	 *
	 * @param customerId
	 *            the customer id
	 */
	public void deleteAddress(Integer customerId) {
		Assert.notNull(customerId, "The customer must not be null");
		log.info("DeleteCustomerAddress{}", customerId);
		final String DELETE_SQL = "delete from address where customer_id = ?";

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(DELETE_SQL, new String[] { "customer_id" });
				ps.setInt(1, customerId);
				return ps;
			}
		});
	}

	/**
	 * Gets the address.
	 *
	 * @param customerId
	 *            the customer id
	 * @return the address
	 */
	private List<Address> getAddress(Integer customerId) {
		Assert.notNull(customerId, "The customer id must not be null");
		log.info("GetCustomerAddress{}", customerId);
		List<Address> addresses = jdbcTemplate.query("select * from address where customer_id = ?",
				new Object[] { customerId }, new AddressRowMapper());

		return addresses;
	}

	/**
	 * The Class CustomerRowMapper.
	 */
	class CustomerRowMapper implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setCustomerId(rs.getInt("customer_id"));
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			customer.setDateOfBirth(DateConverter.toXmlDate(rs.getDate("date_of_birth")));
			customer.setEmailAddress(rs.getString("email_address"));
			return customer;
		}
	}

	/**
	 * The Class AddressRowMapper.
	 */
	class AddressRowMapper implements RowMapper<Address> {
		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();
			address.setAddressType(AddressType.fromValue(rs.getString("address_type")));
			address.setPostCode(rs.getInt("post_code"));
			address.setState(rs.getString("state"));
			address.setStreetName(rs.getString("street_name"));
			address.setStreetNumber(rs.getString("street_number"));
			address.setUnitNumber(rs.getString("unit_number"));
			address.setSuburb(rs.getString("suburb"));
			return address;
		}
	}

}
