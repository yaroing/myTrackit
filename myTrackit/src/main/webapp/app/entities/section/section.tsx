import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISection } from 'app/shared/model/section.model';
import { getEntities } from './section.reducer';

export const Section = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const sectionList = useAppSelector(state => state.mytrackit.section.entities);
  const loading = useAppSelector(state => state.mytrackit.section.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="section-heading" data-cy="SectionHeading">
        <Translate contentKey="myTrackitApp.section.home.title">Sections</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myTrackitApp.section.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/section/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myTrackitApp.section.home.createLabel">Create new Section</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sectionList && sectionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myTrackitApp.section.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.section.sectionNom">Section Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.section.chefSection">Chef Section</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.section.emailChef">Email Chef</Translate>
                </th>
                <th>
                  <Translate contentKey="myTrackitApp.section.phoneChef">Phone Chef</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sectionList.map((section, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/section/${section.id}`} color="link" size="sm">
                      {section.id}
                    </Button>
                  </td>
                  <td>{section.sectionNom}</td>
                  <td>{section.chefSection}</td>
                  <td>{section.emailChef}</td>
                  <td>{section.phoneChef}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/section/${section.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/section/${section.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/section/${section.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myTrackitApp.section.home.notFound">No Sections found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Section;
