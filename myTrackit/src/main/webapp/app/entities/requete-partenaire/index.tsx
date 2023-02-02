import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequetePartenaire from './requete-partenaire';
import RequetePartenaireDetail from './requete-partenaire-detail';
import RequetePartenaireUpdate from './requete-partenaire-update';
import RequetePartenaireDeleteDialog from './requete-partenaire-delete-dialog';

const RequetePartenaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequetePartenaire />} />
    <Route path="new" element={<RequetePartenaireUpdate />} />
    <Route path=":id">
      <Route index element={<RequetePartenaireDetail />} />
      <Route path="edit" element={<RequetePartenaireUpdate />} />
      <Route path="delete" element={<RequetePartenaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequetePartenaireRoutes;
