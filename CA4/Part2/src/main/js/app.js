'use strict';

// tag::vars[]
const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
const client = require('./client'); // <3>
// end::vars[]

// tag::app[]
class App extends React.Component { // <1>

	constructor(props) {
		super(props);
		this.state = {employees: []};
	}

	componentDidMount() { // <2>
		client({method: 'GET', path: '/api/employees'}).done(response => {
			this.setState({employees: response.entity._embedded.employees});
		});
	}

	render() { // <3>
		return (
			<EmployeeList employees={this.state.employees}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table style={{width: '100%', borderCollapse: 'collapse'}}>
				<tbody>
					<tr>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>First Name</th>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>Last Name</th>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>Description</th>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>Job Title</th>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>Years in Job</th>
						<th style={{border: '1px solid #ddd', padding: '8px'}}>Email</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}

// end::employee-list[]

// tag::employee[]
class Employee extends React.Component{
	render() {
		return (
			<tr style={{borderBottom: '1px solid #ddd'}}>
				<td style={{padding: '8px'}}>{this.props.employee.firstName}</td>
				<td style={{padding: '8px'}}>{this.props.employee.lastName}</td>
				<td style={{padding: '8px'}}>{this.props.employee.description}</td>
				<td style={{padding: '8px'}}>{this.props.employee.jobTitle}</td>
				<td style={{padding: '8px'}}>{this.props.employee.jobYears}</td>
				<td style={{padding: '8px'}}>{this.props.employee.email}</td>
			</tr>
		)
	}
}

// end::employee[]

// tag::render[]
ReactDOM.render(
	<App/>,
	document.getElementById('react')
)
// end::render[]
