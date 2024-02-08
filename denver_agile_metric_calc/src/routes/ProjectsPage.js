// // ProjectsPage.js
// import React from 'react';
// import { useParams } from 'react-router-dom';
// import '../components/ProjectComponent.css';

// const ProjectsPage = () => {
// 	/* const [projects, setProjects] = useState([]);
// 	let [searchParams] = useSearchParams(); */
// 	const { memberID } = useParams()
//   	console.log("memberID= " + memberID);
//   	const getProjects = async () => {
//     const projectsUrl = `http://localhost:8080/api/projects/${memberID}`
//       const projectsResponse = await fetch(projectsUrl, {method: 'GET'})
//       if (projectsResponse.ok) {
//         const projects = await projectsResponse.json()
//         return projects
//       } else {
//         throw projectsResponse
//       }
//   	}
// 	const projectList = getProjects()
// 	console.log(projectList)

// /* 	useEffect(() => {
// 		fetch(
// 			`${
// 				process.env.REACT_APP_LOCAL_BASE_URL
// 			}/api/getProjectList/${searchParams.get('memberId')}`
// 		)
// 			.then((response) => response.json())
// 			.then((data) => {
// 				// Handle the fetched data
// 				console.log(data);

// 				setProjects(
// 					data.map((project) => (
// 						<Fragment key={project.projectId}>
// 							<div className='project-item'>
// 								<img
// 									className='project-image'
// 									src={getImageUrl(project)}
// 									alt={project.projectName}
// 								/>
// 								<div className='project-details'>
// 									<Link
// 										to={`/projects/${project.projectName}`}
// 										className='project-link'
// 									>
// 										<b>{project.projectName}</b>
// 									</Link>
// 									<p>{project.slug}</p>
// 								</div>
// 							</div>
// 						</Fragment>
// 					))
// 				);
// 			})
// 			.catch((error) => {
// 				// Handle errors
// 				console.error('Error fetching data:', error);
// 			});
// 	}, []); */

// 	const formatDate = (dateString) => {
// 		const options = { year: 'numeric', month: 'long', day: 'numeric' };
// 		return new Date(dateString).toLocaleDateString(undefined, options);
// 	};


// 	return (
// 		<div>
// 			<div className='project-list-heading'>User Projects</div>
// 			{/* <div className='project-list'>{projectList}</div> */}
// 		</div>
// 	);
// }

// export default ProjectsPage




// // ProjectsPage.js
// import React, { useEffect, useState } from 'react';
// import { useParams } from 'react-router-dom';
// import '../components/ProjectComponent.css';

// const ProjectsPage = () => {
//   const { memberID } = useParams();
//   const [projectList, setProjectList] = useState([]);

//   useEffect(() => {
//     const getProjects = async () => {
//       try {
//         const projectsUrl = `http://localhost:8080/api/projects/${memberID}`;
//         const projectsResponse = await fetch(projectsUrl, { method: 'GET' });

//         if (projectsResponse.ok) {
//           const projects = await projectsResponse.json();
//           setProjectList(projects);
//         } else {
//           throw projectsResponse;
//         }
//       } catch (error) {
//         console.error('Error fetching projects:', error);
//       }
//     };

//     getProjects();
//   }, [memberID]);

//   const formatDate = (dateString) => {
//     const options = { year: 'numeric', month: 'long', day: 'numeric' };
//     return new Date(dateString).toLocaleDateString(undefined, options);
//   };

//   return (
//     <div>
//       <div className='project-list-heading'>User Projects</div>
//       <div className='project-list'>
//         {projectList.map((project) => (
//           <div key={project.projectID}>
//             <div>Project ID: {project.projectID}</div>
//             <div>Project Name: {project.projectName}</div>
//             <div>Slug: {project.slug}</div>
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };

// export default ProjectsPage;

// Updated ProjectsPage.js
import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../components/ProjectComponent.css';

const ProjectsPage = () => {
  const { memberID } = useParams();
  const [projectList, setProjectList] = useState([]);

  useEffect(() => {
    const getProjects = async () => {
      try {
        const projectsUrl = `http://localhost:8080/api/projects/${memberID}`;
        const projectsResponse = await fetch(projectsUrl, { method: 'GET' });

        if (projectsResponse.ok) {
          const projects = await projectsResponse.json();
          setProjectList(projects);
        } else {
          throw projectsResponse;
        }
      } catch (error) {
        console.error('Error fetching projects:', error);
      }
    };

    getProjects();
  }, [memberID]);

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  return (
    <div>
      <div className='project-list-heading'>User Projects</div>
      <table className='project-table'>
        <thead>
          <tr>
            <th>Project ID</th>
            <th>Project Name</th>
            <th>Slug</th>
            {/* Add more headers as needed */}
          </tr>
        </thead>
        <tbody>
          {projectList.map((project) => (
            <tr key={project.projectID}>
              <td>{project.projectID}</td>
              <td className="project-name">
                <Link to={`/projects/${memberID}/${project.projectID}`}>{project.projectName}</Link>
              </td>
              {/* <td>{project.projectName}</td> */}
              <td>{project.slug}</td>
              {/* Add more cells as needed */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProjectsPage;
